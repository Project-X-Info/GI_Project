package router;

import java.util.*;

import GUI.MsgBox;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileWriter;

import adjacencylist.*;
import datamanagement.*;
import graph.*;
import router.*;
import utilities.MinHeap;

/*
 * 	author Oliver Geißendörfer
 */

public class RouteFinder {

	static Graph_AdjList graph;

	public RouteFinder() {
		RouteFinder.graph = null;
	}

	public RouteFinder(Graph_AdjList g) {
		RouteFinder.graph = g;
	}

	public void setGraph(Graph_AdjList g) {
		RouteFinder.graph = g;
	}

	public static Graph_AdjList getGraph() {
		return graph;
	}

	// finden eines geeigneten Startknotens (wird nicht benutzt)
	public Node _setStartpoint(ArrayList<Node> nodeList, POI start, Graph_AdjList graph) {

		double x = start.getX();
		double y = start.getY();
		double d = Math.pow((x), 2) + Math.pow((y), 2);
		int s_index = 0;

		NodeList[] lines = graph.getAdjacencyList();

		for (int i = 1; i < nodeList.size(); i++) {

			Node node = nodeList.get(i);
			double sx = node.getX();
			double sy = node.getY();

			double distance = Math.pow((x - sx), 2) + Math.pow((y - sy), 2);
			NodeList line = lines[i];
			if (line.getSize() > 3) {
				if (distance < d) {
					d = distance;

					s_index = i;
				}
			}
		}
		Node startnode = nodeList.get(s_index);

		return startnode;
	}

	/*
	 * finden des nächsten Knotens zum POI dieser muss mindestens 3 Kanten in
	 * der adjazenzenliste besitzen
	 */
	public int findnearNode(POI start) {
		Node[] knoten = graph.getNodeArray();
		NodeList[] adjlist = graph.getAdjacencyList();

		double x = start.getX();
		double y = start.getY();
		double d = Double.MAX_VALUE;

		int s_idx = -1;

		for (int i = 0; i < knoten.length; i++) {
			double sx = knoten[i].getX();
			double sy = knoten[i].getY();
			double distance = Math.pow((x - sx), 2) + Math.pow((y - sy), 2);
			if (adjlist[i].getSize() > 3) {
				if (distance < d) {
					d = distance;
					s_idx = i;
				}
			}
		}
		return knoten[s_idx].getNodeId();
	}

	/*
	 * Überprüfen des Rundkurses; ob ein oder mehrere POI innerhalb liegen wird
	 * noch nicht ausgeschlossen
	 */
	private int PunktInPolygon(double x, double y, ArrayList<Node> path, Node[] knots) {
		/*
		 * https://de.wikipedia.org/wiki/Punkt-in-Polygon-Test_nach_Jordan
		 * Parameter: Ecken P[1], ...,P[n] eines ebenen Polygons P, Testpunkt Q
		 * Rückgabe: +1, wenn Q innerhalb P liegt; -1, wenn Q außerhalb P
		 * liegt; 0, wenn Q auf P liegt
		 */
		int t = -1;
		for (int i = 1; i < path.size(); i++) {
			double ax = path.get(i - 1).getX();
			double ay = path.get(i - 1).getY();
			double bx = path.get(i).getX();
			double by = path.get(i).getY();
			t = t * KreuzProdTest(x, y, ax, ay, bx, by);
			if (t == 0)
				break;
		}
		return t;
	}

	private int KreuzProdTest(double ax, double ay, double bx, double by, double cx, double cy) {
		/*
		 * https://de.wikipedia.org/wiki/Punkt-in-Polygon-Test_nach_Jordan
		 * Rückgabe: -1, wenn der Strahl von A nach rechts die Kante [BC]
		 * schneidet (außer im unteren Endpunkt); 0, wenn A auf [BC] liegt;
		 * sonst +1
		 */
		if (ay == by && ay == cy) {
			if ((bx <= ax && ax <= cx) || (cx <= ax && ax <= bx)) {
				return 0;
			} else {
				return +1;
			}
		}
		if (ay == by && ax == bx) {
			return 0;
		}
		if (by > cy) {
			double x, y;
			x = bx;
			y = by;
			bx = cx;
			by = cy;
			cx = x;
			cy = y;
		}
		if (ay <= by || ay >= cy) {
			return +1;
		}
		double Delta = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax);
		if (Delta > 0) {
			return -1;
		} else {
			if (Delta < 0) {
				return +1;
			} else {
				return 0;
			}
		}
	}

	public void find(int srcId) {
		find(srcId, null);
	}

	/***********************************
	 * Rundkursfindung ++++++++++++++++++++++++++++++++++
	 */
	public void find(int srcId, ArrayList<POI> exList) {

		Dijkstra finder = new Dijkstra();
		finder.setGraph(graph);

		// Zielwert und Toleranzen
		int ntrials = 4; // 3; // 2;
		double drundkurs = 21500;
		double tolseite = 3000.; // 2000.; // 1000.;
		double tolkurs = 500.;
		double tolangle = 0.5; // 0.5;
		int tolconnects = 5; // 4; //3;

		int src = graph.getIndex(srcId);
		System.out.printf("Startpunkt %d [%d]:\n", srcId, src);

		if (exList != null) {
			for (POI p : exList) {
				System.out.println("Excludepunkt " + p.getName());
			}
		} else {
			System.out.println("kein Excludepunkt angegeben");
		}

		Node[] knots = graph.getNodeArray();
		NodeList[] adj = graph.getAdjacencyList();

		// Speichern der Pfade
		ArrayList<Node> ipath = new ArrayList<Node>();
		ArrayList<Node> jpath = new ArrayList<Node>();
		ArrayList<Node> ijpath = new ArrayList<Node>();
		ArrayList<Node> path = new ArrayList<Node>();

		// Initialisierung der Dijkstra-Resultate
		DijkstraResult idijkstra = new DijkstraResult();
		DijkstraResult jdijkstra = new DijkstraResult();
		DijkstraResult ijdijkstra = new DijkstraResult();

		graph.hideClear();
		idijkstra = jdijkstra = finder.dijkstra(src);

		int nrundkurse = 0;
		int ibest = -1, jbest = -1;
		double dbest = Double.MAX_VALUE, rbest = 0.;

		double x = knots[src].getX();
		double y = knots[src].getY();

		int count = 0;

		MsgBox.setOutput("Rundkurse:");
		MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// finde Weg von src nach i
		for (int i = 0; i < knots.length; i++) {

			double xi = knots[i].getX();
			double yi = knots[i].getY();
			double nxi = xi - x;
			double nyi = yi - y;
			double d = Math.sqrt(Math.pow(nxi, 2) + Math.pow(nyi, 2));
			if (d > drundkurs / 3)
				continue;
			nxi /= d;
			nyi /= d;

			// Kriterium des Weges src nach i
			if (idijkstra.mindist(i) < (drundkurs / 3. - tolseite) || idijkstra.mindist(i) > (drundkurs / 3 + tolseite)
					|| adj[i].getSize() < tolconnects)
				continue;

			graph.hideClear();
			ipath.clear();
			ipath = idijkstra.minpath(i);
			// System.out.println("Main "+(ipath.size()-1)+"
			// "+ipath.get(ipath.size()-1).getNodeId()+"
			// ["+ipath.get(ipath.size()-1).getIndex()+"]");

			// ausschließen des Pfades i für weitere Berechnungen
			for (int k = ipath.size() - 2; k > 0; k--) {
				graph.hideNode(ipath.get(k).getIndex());
				// System.out.println("main "+k+" "+ipath.get(k).getNodeId()+"
				// ["+ipath.get(k).getIndex()+"]");
			}
			// System.out.println("Main "+0+" "+ipath.get(0).getNodeId()+"
			// ["+ipath.get(0).getIndex()+"]");
			jdijkstra = finder.dijkstra(src); // dijkstra muss nochmal
												// berechnen, da ein teil der
												// Wege ausgeschlossen worden
												// sind

			// finde Weg vo src nach j ( == Weg von j nach src)
			for (int j = 0; j < knots.length; j++) {

				if (i == j)
					continue;

				count += 1;
				if ((count % 80000) == 0) {
					System.out.println(""); // still alive echo
				} else if ((count % 1000) == 0) {
					System.out.print(".");
				}

				// ausschließen von Knoten die unmöglich auf dem Rundkurs
				// liegen können
				if (knots[j].gethide())
					continue;

				double xj = knots[j].getX();
				double yj = knots[j].getY();
				double nxj = xj - x;
				double nyj = yj - y;
				d = Math.sqrt(Math.pow(nxj, 2) + Math.pow(nyj, 2));
				if (d > drundkurs / 3)
					continue;
				nxj /= d;
				nyj /= d;
				d = nxi * nxj + nyi * nyj;
				// ausschließen eines Rundkurses, der eine StraÃŸe hoch ung
				// wieder runter lÃ¤uft durch EinfÃ¼hrung eines mindest Winkels
				if (d > tolangle)
					continue; // cos(angle) zwischen src->i and src->j

				d = Math.sqrt(Math.pow((xi - xj), 2) + Math.pow((yi - yj), 2));
				if (d > (drundkurs - idijkstra.mindist(i) - jdijkstra.mindist(j)))
					continue;

				// Kriterium des Weges src nach j mit Seitenlängen Toleranz
				if (jdijkstra.mindist(j) < (drundkurs / 3. - tolseite)
						|| jdijkstra.mindist(j) > (drundkurs / 3 + tolseite) || adj[j].getSize() < tolconnects)
					continue;

				jpath.clear();
				jpath = jdijkstra.minpath(j);

				// ausschließen des Pfades j für weitere Berechnungen
				for (int k = jpath.size() - 2; k > 0; k--) {
					graph.hideNode(jpath.get(k).getIndex());
				}

				// suche Weg von i nach j
				ijdijkstra = finder.dijkstra(i);

				// ÜberprÃ¼fen der Rundkursstrecke durch Summation
				double r = idijkstra.mindist(i) + ijdijkstra.mindist(j) + jdijkstra.mindist(j);
				// System.out.println("R "+i+" "+j+" "+r+"
				// "+idijkstra.mindist(i)+","+ijdijkstra.mindist(j)+","+jdijkstra.mindist(j));;

				// Speichern des besten Rundkurses = speichern der Knoten i und
				// j
				if ((drundkurs - tolkurs) < r && r < (drundkurs + tolkurs)) {
					nrundkurse += 1;
					if (dbest > Math.abs(drundkurs - r)) {
						dbest = Math.abs(drundkurs - r);
						ibest = i;
						jbest = j;
						rbest = r;

						ipath = idijkstra.minpath(ibest);
						jpath = jdijkstra.minpath(jbest);
						ijpath = ijdijkstra.minpath(jbest);
						path.clear();

						// Zusammenfügen der verschiedenen Pfade um eine
						// einheitliche Transformation to kml zu ermöglichen
						for (int k = ipath.size() - 1; k >= 0; k--) {
							path.add(ipath.get(k));
							// System.out.println("ipath "+k+" Idx =
							// "+ipath.get(k).getIndex()+" Id =
							// "+ipath.get(k).getNodeId());
						}

						// System.out.println("ijpath "+(ijpath.size()-1)+" Idx
						// = "+ijpath.get((ijpath.size()-1)).getIndex()+" Id =
						// "+ijpath.get((ijpath.size()-1)).getNodeId());
						for (int k = ijpath.size() - 2; k > 0; k--) {
							path.add(ijpath.get(k));
							// System.out.println("ijpath "+k+" Idx =
							// "+ijpath.get(k).getIndex()+" Id =
							// "+ijpath.get(k).getNodeId());
						}
						// System.out.println("ijpath "+0+" Idx =
						// "+ijpath.get(0).getIndex()+" Id =
						// "+ijpath.get(0).getNodeId());

						for (int k = 0; k < jpath.size(); k++) {
							path.add(jpath.get(k));
							// System.out.println("jpath "+k+" Idx =
							// "+jpath.get(k).getIndex()+" Id =
							// "+jpath.get(k).getNodeId());
						}

						// ausschlieÃŸen der POIs die nicht im Rundkurs liegen
						// sollen
						if (exList != null) {
							int repeat = 0;
							for (POI p : exList) {
								int iex = PunktInPolygon(p.getX(), p.getY(), path, knots);
								if (iex >= 0) {
									MsgBox.setOutput(String.format(
											"\nRundkurs %d - %d - %d - %d gefunden, aber POI %s innerhalb!\n", src, i,
											j, src, p.getName()));
									System.out.printf(
											"\nRundkurs %d - %d - %d - %d gefunden, aber exId %s inside %d!\n", src, i,
											j, src, p.getName(), iex);

									dbest = Double.MAX_VALUE;
									repeat = 1;
									break;
								} else {
									MsgBox.setOutput(String.format(
											"Rundkurs %d - %d - %d - %d gefunden und  POI %s außerhalb!\n", src, i, j,
											src, p.getName()));
									System.out.printf(
											"\nRundkurs %d - %d - %d - %d gefunden und  exId %s outside %d!\n", src, i,
											j, src, p.getName(), iex);
								}
							}
							if (repeat != 0)
								continue;
						} else {
							// System.out.printf("Rundkurs %d - %d - %d - %d
							// gefunden: dist = %10.3f = %10.3f + %10.3f +
							// %10.3f\n",src,i,j,src,r,sdist[i],ij,sdist[j]);
						}

						boolean stop4first = true; // höre auf, sobald ein
													// erster Rundkurs gefunden
						if (stop4first) {
							i = j = knots.length; // Abbruch-Kriterium
						} else {
							ntrials -= 1;
							if (ntrials == 0)
								i = j = knots.length; // Abbruch-Kriterium
						}
					}
				}
			}
		}

		if (path.size() > 3) {

			System.out.println("");

			int iex = 1;
			int niex = 0;
			if (exList != null) {
				for (POI p : exList) {
					iex = PunktInPolygon(p.getX(), p.getY(), path, knots);
					if (iex >= 0) {
						niex += 1;
						System.out.println("P exId = " + p.getName() + "  Inside " + iex);
					} else {
						System.out.println("P exId = " + p.getName() + "  Outside " + iex);
					}
				}
			}

			// Koordinaten-Transformation und kml Writer
			CoordTraf.setLonLat_Nodes(path);
			try {
				Write.kmlWriter(path, exList, rbest, "blue", 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
			MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			MsgBox.setOutput(String.format("Insgesamt %d Rundkurse gefunden!", nrundkurse));
			MsgBox.setOutput(String.format("Beste Distanz : %10.3f m", rbest));

			System.out.printf("Insgesamt %d Rundkurse gefunden!\n", nrundkurse);
			System.out.printf("bester Rundkurs %d - %d - %d - %d: best dist = %10.3f", src, ibest, jbest, src, rbest);
			if (exList != null && niex > 0) {
				MsgBox.setOutput("Ausschluss allerdings fehlgeschlagen!");
				System.out.printf(", allerdings exclude fehlgeschlagen!\n");
			} else
				System.out.printf("\n");
		} else {
			MsgBox.setOutput("Keinen Rundkurs gefunden!");
			System.out.println("\nKeinen Rundkurs gefunden!");
			System.exit(0);
		}
		MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	}

}

package adjacencylist;

import java.util.*;
import graph.*;
import utilities.*;

/*
 * author Michael Recla
 */
public class Graph_AdjList {

	static boolean debug = false;

	// Array aller Punkte
	public Node[] nodeArray;
	// Array aller Punkte, Index entspricht der nodeId (Zwischenraeume null)
	public Node[] nodeArray_id;
	// Adjazenzliste als Array von Listen
	private NodeList[] adjacencyList;

	/*
	 * Für ihre anschließende Befüllung wird das Grundgerüst der Adjazenzliste
	 * errichtet. D. h. für jeden Knoten wird eine Liste für dessen Nachbarn
	 * bereitgestellt.
	 */
	public void initAdjacencyList() {
		int nodeNumber = nodeArray.length;
		adjacencyList = new NodeList[nodeNumber]; // Array aus Listen
		for (int i = 0; i < nodeNumber; i++) {
			adjacencyList[i] = new NodeList();// Liste für Nachbarn von
												// Knoten i
		}
		System.out.println("Adajzenzliste wurde initialisiert!");
	}

	/*
	 * Mit setLines() werden in der Adjazenzliste die Listen der Nachbarn
	 * gefüllt. Jede Kante mit 'node1' und 'node2' enthält die IDs der Knoten,
	 * welche miteinander verbunden sind. Damit können diese Nachbarn aus dem
	 * nodeArray_id entnommen werden (Id und Index stimmen hier überein).
	 */
	public void setLines(List<Line> lines) {
		long start = new Date().getTime();

		System.out.println("Kanten werden gesetzt...  " + lines.size() + " Lines");
		int nl = 0;
		for (Line Line : lines) {
			int from = Line.getNodeId1();
			int to = Line.getNodeId2();

			if (debug) {
				nl += 1;
				if (nl % 100 == 0)
					System.out.println("... down " + (lines.size() - nl) + " setLine: " + Line.getLineId() + ":   "
							+ from + " - " + to);
			}

			// Umsetzung der Knoten-Id nach Knotenindex, da adjcencyList[i] die
			// zu nodeArray[i] entsprechende Adjazenzliste sein soll
			from = getIndex(from);
			to = getIndex(to);
			// Berechnung der jeweiligen Strecke
			double xf = nodeArray[from].getX();
			double yf = nodeArray[from].getY();
			double xt = nodeArray[to].getX();
			double yt = nodeArray[to].getY();
			// Berechnung der Distanz von Knoten from nach Knoten to
			double dist = Math.sqrt(Math.pow((xf - xt), 2) + Math.pow((yf - yt), 2));

			NodeList fromList = adjacencyList[from];
			fromList.add(nodeArray[to], dist);
			// Graph nicht gerichtet --> auch umgekehrt anfügen
			NodeList toList = adjacencyList[to];
			toList.add(nodeArray[from], dist);
		}

		long end = new Date().getTime() - start;
		System.out.println("line set time: " + end);
		System.out.println("Kanten sind gesetzt!");
	}

	public int getIndex(int nodeID) {
		return nodeArray_id[nodeID].getIndex();
	}

	public Node getNodefromID(int nodeID) {
		return nodeArray_id[nodeID];
	}

	// fuer Routenplanung
	public void hideNode(int i) {
		nodeArray[i].sethide(true);
		// System.out.println("Hide node "+i);
	}

	public void hideClear() {
		for (int i = 0; i < nodeArray.length; i++) {
			nodeArray[i].sethide(false);
		}
	}

	// Getter und Setter

	public void setNodeArray_id(List<Node> nodeList) {
		nodeArray_id = new Node[Node.getMaxId() + 1];
		ArrayList<Node> tmp_nodes = new ArrayList<Node>();
		// nodeArray ohne Zwischenraeume = null
		for (Node node : nodeList) {
			nodeArray_id[node.getNodeId()] = node;
			tmp_nodes.add(node);
		}
		nodeArray = tmp_nodes.toArray(new Node[tmp_nodes.size()]);
	}

	public Node[] getNodeArray_id() {
		return nodeArray_id;
	}

	public void setNodeArray_id(Node[] nodeArray_id) {
		this.nodeArray_id = nodeArray_id;
	}

	public Node[] getNodeArray() {
		return nodeArray;
	}

	public void setNodeArray(Node[] nodeArray) {
		this.nodeArray = nodeArray;
	}

	public NodeList[] getAdjacencyList() {
		return adjacencyList;
	}

	// Print-Methoden für die Konsole

	public void printNodeArray() {
		System.out.println("----------------------------------------");
		for (int i = 0; i < nodeArray.length; i++) {
			System.out.println("[" + nodeArray[i].getIndex() + "] " + nodeArray[i].getNodeId() + "\t"
					+ nodeArray[i].getX() + "\t" + nodeArray[i].getY());
		}
	}

	public void printAdjacencylist() {
		System.out.println("----------------------------------------");
		System.out.println("Index\tHead\t\tNeighbours...");
		System.out.println("----------------------------------------");
		for (int i = 0; i < adjacencyList.length; i++) {
			System.out.print("[" + i + "]\t" + nodeArray[i].getNodeId());
			adjacencyList[i].printElements();
			System.out.println();
		}
	}
	
	/*
	 * Durch die Verwendung des nodeArray_id kann auf folgende Methoden
	 * verzichtet werden. über das Array darauf zuzugreifen, ist viel
	 * effizienter.
	 */
	
	// public Node _getNodefromID(int nodeID) { // getNodefromId mit
	// sequentieller
	// // Suche
	// Node k = null;
	// for (int i = 0; i < nodeArray.length; i++) {
	// if (nodeArray[i].getNodeId() == nodeID) {
	// k = nodeArray[i];
	// break;
	// }
	// }
	// if (k == null) {
	// System.out.println("Der gesuchte Node ist nicht enthalten!");
	// }
	// return k;
	// }
	//
	// public Node getNodefromId(int nodeID) { // getNodefromId mit binärer
	// Suche
	// int mitte;
	// int links = 0;
	// int rechts = nodeArray.length - 1;
	//
	// while (links <= rechts) {
	// mitte = (links + rechts) / 2; /* Bereich halbieren */
	//
	// if (nodeArray[mitte].getNodeId() == nodeID) {
	// return nodeArray[mitte];
	// } else {
	// if (nodeArray[mitte].getNodeId() > nodeID) {
	// rechts = mitte - 1; /* im linken Abschnitt weitersuchen */
	// } else {
	// links = mitte + 1; /* im rechten Abschnitt weitersuchen */
	// }
	// }
	// }
	//
	// return null;
	// }

	// public int _getIndex(int nodeID) { // getIndex mit sequentieller Suche
	// int index = -1;
	// for (int i = 0; i < nodeArray.length; i++) {
	// if (nodeArray[i].getNodeId() == nodeID) {
	// index = i;
	// break;
	// }
	// }
	// if (index == -1) {
	// System.out.println("Der gesuchte Node ist nicht enthalten!");
	// }
	// return index;
	// }

	// public int getIndex(int nodeID) { // getIndex binäre Suche
	// int mitte;
	// int links = 0;
	// int rechts = nodeArray.length - 1;
	//
	// while (links <= rechts) {
	// mitte = (links + rechts) / 2; /* Bereich halbieren */
	//
	// if (nodeArray[mitte].getNodeId() == nodeID) {
	// return mitte;
	// } else {
	// if (nodeArray[mitte].getNodeId() > nodeID) {
	// rechts = mitte - 1; /* im linken Abschnitt weitersuchen */
	// } else {
	// links = mitte + 1; /* im rechten Abschnitt weitersuchen */
	// }
	// }
	// }
	//
	// return -1;
	// }
	
	// public void setNodeArray(List<Node> nodeList) {
	// nodeArray = new Node[nodeList.size()];
	//
	// int i = 0;
	// for (Node node : nodeList) {
	// nodeArray[i++] = node;
	// }
	//
	// long time_start = new Date().getTime();
	// System.out.println("Sortieren Suche Start ...");
	// // QuickSort qs = new QuickSort(nodeArray);
	// // qs.sortiere(); // Stack overflow
	// BubbleSort bs = new BubbleSort(nodeArray);
	// bs.sortiere();
	// long time_end = new Date().getTime() - time_start;
	// System.out.println("\n... Sortieren Ende time: " + time_end);
	//
	// // nach dem Sortieren hat sich der index geändert!
	// for (i = 0; i < nodeArray.length; i++) {
	// nodeArray[i].setIndex(i);
	// }
	// }

}

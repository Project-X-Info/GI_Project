package main;

import java.util.ArrayList;
import GUI.*;
import adjacencylist.*;
import java.io.IOException;
import datamanagement.CoordTraf;
import datamanagement.Read;
import graph.Line;
import graph.Node;
import graph.POI;
import router.*;

public class Main {

	public static void main(String[] args) throws IOException {

		// Startfenster (SplashScreen) initiieren
		SplashScreen sc = new SplashScreen("/files/startIMG.jpg");

		ArrayList<Node> nodeList = new ArrayList<Node>();
		ArrayList<Line> lineList = new ArrayList<Line>();
		ArrayList<POI> POIList = new ArrayList<POI>();

		// Einlesen der gml-Files
		Read.readNodes("/files/Roads_Munich_Route_Node.gml", nodeList);
		Read.readLines("/files/Roads_Munich_Route_Line.gml", lineList);
		Read.readPOIs("/files/Points_Munich_Route_POIs.gml", POIList);

		// Erstellung des Graphen
		Graph_AdjList graph = new Graph_AdjList();
		graph.setNodeArray_id(nodeList);
		graph.initAdjacencyList();
		// Kanten werden in der Adjazenzliste gesetzt und diese befüllt
		graph.setLines(lineList);

		// alle POIs werden transformiert (für Karte in GUI)
		CoordTraf.setLonLat_POI(POIList);

		// Routfinder wird initiiert
		new RouteFinder(graph);
		
		// SplashScreen wird geschlossen
		sc.close();
		// GUI wird initiiert
		new GUI(POIList);

		System.out.println("*************************************************");

		// Methoden zum Testen:

		// System.out.println("NodeArray
		// ************************************************");
		// graph.printNodeArray();
		// System.out.println("AdjacencyList
		// ********************************************");
		// graph.printAdjacencylist();
		// System.out.println("*************************************************");
		
		// POI s;
		// s = POIList.get(0); // sollte Ratskeller sein
		// s = POIList.get(3); // sollte Sendlinger Tor sein
		// s = POIList.get(33); // sollte Isartor sein
		// s = POIList.get(42); // sollte Residenz sein
		// s = POIList.get(80); // sollte Theresienwiese sein
		// s = POIList.get(83); // sollte Hauptbahnhof sein
		// s = POIList.get(112); // sollte Königsplatz sein
		// s = POIList.get(787); // sollte Stiglmaierplatz sein
		// int startId = rout.findnearNode(s); // Startpunkt finden
		// System.out.println("Start POI: "+startId+" "+s.getName());
		//
		// boolean ohneEx = false;
		// if (ohneEx) { // Rundkursfindung ohne Ausschluss von POI
		// rout.find(startId);
		// } else {
		// ArrayList<POI> exList = new ArrayList<POI>();
		// s = POIList.get(83); // sollte Hauptbahnhof sein
		// exList.add(s);
		// s = POIList.get(1598); // sollte Rosenheimer Platz sein
		// exList.add(s);
		// rout.find(startId,exList);
		// }

		// CoordTraf.setLonLat(nodeList);

		// CoordTraf.printCoord(nodeList);

		// Write.kmlWriter2(graph, lineList, "kmlTest.kml", "blue");

		// System.out.println("Test");

		/*
		 * PROBECODE AUSGABE TXT-FILE FileWriter fw = null; BufferedWriter bw =
		 * null; try { fw = new FileWriter("Koordinaten.txt"); bw = new
		 * BufferedWriter(fw); // erste Zeile wird geschrieben bw.write("y x");
		 * bw.newLine(); // while-Schleife um alle Zeilen zu schreiben for (int
		 * i = 0; i < nodeList.size(); i++) {
		 * bw.write(String.valueOf(nodeList.get(i).getY())); bw.write(" " +
		 * String.valueOf(nodeList.get(i).getX())); bw.newLine(); } bw.close();
		 * fw.close(); } catch (IOException e) { e.printStackTrace(); }
		 */
	}
}

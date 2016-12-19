package main;

import java.util.ArrayList;

import adjacencylist.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import datamanagement.Read;
import graph.Line;
import graph.Node;
import graph.POI;

public class Main {

	public static void main(String[] args) {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		ArrayList<Line> lineList = new ArrayList<Line>();
		ArrayList<POI> POIList = new ArrayList<POI>();
		// Aufruf der statischen Methoden
		Read.readNodes("Roads_Munich_Route_Node.gml",nodeList);
		Read.readLines("Roads_Munich_Route_Line.gml",lineList);
		Read.readPOIs("Points_Munich_Route_POIs.gml",POIList);
		
		Graph_AdjList graph = new Graph_AdjList();
		graph.setNodeArray_id(nodeList);
		graph.initAdjacencyList();
		graph.setLines(lineList);
		
		
		graph.printAdjacencylist();
		
		//System.out.println("Test");
		
		/* PROBECODE AUSGABE TXT-FILE
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("Koordinaten.txt");
			bw = new BufferedWriter(fw);
			// erste Zeile wird geschrieben
			bw.write("y x");
			bw.newLine();
			// while-Schleife um alle Zeilen zu schreiben
			for (int i = 0; i < nodeList.size(); i++) {
				bw.write(String.valueOf(nodeList.get(i).getY()));
				bw.write(" " + String.valueOf(nodeList.get(i).getX()));
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}

}

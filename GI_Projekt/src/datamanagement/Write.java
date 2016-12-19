package datamanagement;

import java.io.*;
import java.util.*;

import adjacencylist.*;
import graph.*;

public class Write {

	public static void kmlWriter(Graph_AdjList graph, List<Line> lines, String fileName, String color)
			throws IOException {

		// Farben in einer kml-Datei: Deckkraft;Blau;Grün;Rot von 0..255 (0..ff)
		String colorCode = "";
		if (color.equals("red")) {
			colorCode = "ff0000ff";
		} else if (color.equals("green")) {
			colorCode = "ff00ff00";
		} else if (color.equals("blue")) {
			colorCode = "ffff0000";
		} else if (color.equals("white")) {
			colorCode = "ffffffff";
		}

		FileWriter fw;

		fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		bw.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
		bw.write("<Document>\n");
		bw.write("<name>KML-File</name>\n");

		for (Line line : lines) {

			bw.write("\t<Placemark>\n");
			bw.write("<Style id=\"" + color + "\">\n");
			bw.write("<LineStyle>\n");
			bw.write("<color>" + colorCode + "</color>\n");
			bw.write("<width>1</width>\n");
			bw.write("</LineStyle>\n");
			bw.write("</Style>\n");
			bw.write("\t\t<name>LineID: " + line.getLineId() + "</name>\n");
			bw.write("\t\t<visibility>1</visibility>\n");
			bw.write("\t\t<description>Von: " + line.getNodeId1() + "; Nach: " + line.getNodeId2() + "</description>\n");
			bw.write("\t\t<LineString>\n");
			bw.write("\t\t\t<tessellate>1</tessellate>\n");
			bw.write("\t\t\t<coordinates>\n");
			bw.write("\t\t\t\t" + graph.getNodeArray_id()[line.getNodeId1()].getLon() + ","
					+ graph.getNodeArray_id()[line.getNodeId1()].getLat() + ",0\n");

			bw.write("\t\t\t\t" + graph.getNodeArray_id()[line.getNodeId2()].getLon() + ","
					+ graph.getNodeArray_id()[line.getNodeId2()].getLat() + ",0\n");

			bw.write("\t\t\t</coordinates>\n");
			bw.write("\t\t</LineString>\n");
			bw.write("\t</Placemark>\n");
		}

		bw.write("</Document>\n");
		bw.write("</kml>\n");
		bw.close();

		System.out.println("----------------------------------------");
		System.out.println("Die Datei \"" + fileName + "\" wurde erstellt!");
	}
}

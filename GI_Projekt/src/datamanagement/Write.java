package datamanagement;

import java.io.*;
import java.util.*;

import GUI.MsgBox;
import GUI.Save;
import GUI.Wrapper;
import graph.*;

/*
 * 	author Michael Recla
 */

public class Write {

	public static void kmlWriter(ArrayList<Node> path, ArrayList<POI> exList, double distance, String color,
			int linewidth) throws IOException {

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

		fw = new FileWriter(Save.saveTo());
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		bw.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
		bw.write("<Document>\n");
		bw.write("\t<name>KML-File</name>\n");

		// Style für Rundkurs
		bw.write("\t<Style id=\"style_path\">\n");
		bw.write("\t\t<LineStyle>\n");
		bw.write("\t\t<color>" + colorCode + "</color>\n");
		bw.write("\t\t<width>" + linewidth + "</width>\n");
		bw.write("\t\t</LineStyle>\n");
		bw.write("\t</Style>\n");
		// Rundkurs
		bw.write("\t<Placemark>\n");
		bw.write("\t\t<name>Rundkurs</name>\n");
		bw.write("\t\t<styleUrl>#style_path</styleUrl>\n");
		bw.write(String.format("\t\t<description>L&#228;nge: %10.3f m</description>\n", distance));
		bw.write("\t\t<visibility>1</visibility>\n");
		bw.write("\t\t<LineString>\n");
		bw.write("\t\t\t<tessellate>1</tessellate>\n");
		bw.write("\t\t\t<coordinates>\n");
		for (Node node : path) {
			bw.write("\t\t\t\t" + node.getLon() + "," + node.getLat() + ",0\n");
		}
		bw.write("\t\t\t</coordinates>\n");
		bw.write("\t\t</LineString>\n");
		bw.write("\t</Placemark>\n");

		POI start = Wrapper.startListe.getSelectedPOIs()[0];

		// Style für Startpunkt
		bw.write("\t<Style id=\"style_points\">\n");
		bw.write("\t\t<IconStyle>\n");
		bw.write("\t\t\t<scale>1.2</scale>\n");
		bw.write("\t\t\t<Icon>\n");
		bw.write("\t\t\t\t<href>http://maps.google.com/mapfiles/kml/paddle/grn-blank.png</href>\n");
		bw.write("\t\t\t</Icon>\n");
		bw.write("\t\t\t<hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n");
		bw.write("\t\t</IconStyle>\n");
		bw.write("\t<ListStyle>\n");
		bw.write("\t\t<ItemIcon>\n");
		bw.write("\t\t\t<href>http://maps.google.com/mapfiles/kml/paddle/grn-blank-lv.png</href>\n");
		bw.write("\t\t</ItemIcon>\n");
		bw.write("\t</ListStyle>\n");
		bw.write("\t</Style>\n");
		bw.write("\t<Style id=\"style_points0\">\n");
		bw.write("\t\t<IconStyle>\n");
		bw.write("\t\t\t<scale>1.4</scale>\n");
		bw.write("\t\t\t<Icon>\n");
		bw.write("\t\t\t\t<href>http://maps.google.com/mapfiles/kml/paddle/grn-blank.png</href>\n");
		bw.write("\t\t\t</Icon>\n");
		bw.write("\t\t\t<hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n");
		bw.write("\t\t</IconStyle>\n");
		bw.write("\t\t<ListStyle>\n");
		bw.write("\t\t\t<ItemIcon>\n");
		bw.write("\t\t\t\t<href>http://maps.google.com/mapfiles/kml/paddle/grn-blank-lv.png</href>\n");
		bw.write("\t\t\t</ItemIcon>\n");
		bw.write("\t\t</ListStyle>\n");
		bw.write("\t</Style>\n");
		bw.write("\t<StyleMap id=\"style_points1\">\n");
		bw.write("\t\t<Pair>\n");
		bw.write("\t\t\t<key>normal</key>\n");
		bw.write("\t\t\t<styleUrl>#style_points</styleUrl>\n");
		bw.write("\t\t</Pair>\n");
		bw.write("\t\t\t<Pair>\n");
		bw.write("\t\t\t<key>highlight</key>\n");
		bw.write("\t\t\t<styleUrl>#style_points0</styleUrl>\n");
		bw.write("\t\t</Pair>\n");
		bw.write("\t</StyleMap>\n");

		// Startpunkt
		bw.write("\t<Placemark>\n");
		bw.write("\t\t<name>" + start.getName() + "</name>\n");
		bw.write("\t\t<description>Startpunkt der Routenplanung</description>\n");
		bw.write("\t\t<styleUrl>#style_points1</styleUrl>\n");
		bw.write("\t\t<Point>\n");
		bw.write("\t\t\t<coordinates>" + start.getLon() + "," + start.getLat() + ",0</coordinates>");
		bw.write("\t\t</Point>\n");
		bw.write("\t</Placemark>\n");

		// Style für auszuschließende Punkte
		bw.write("\t<Style id=\"sn_caution\">\n");
		bw.write("\t\t<IconStyle>\n");
		bw.write("\t\t\t<scale>1.0</scale>\n");
		bw.write("\t\t\t<Icon>\n");
		bw.write("\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/caution.png</href>\n");
		bw.write("\t\t\t</Icon>\n");
		bw.write("\t\t\t<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\n");
		bw.write("\t\t</IconStyle>\n");
		bw.write("\t<ListStyle>\n");
		bw.write("\t\t<ItemIcon>\n");
		bw.write("\t\t\t<href>http://maps.google.com/mapfiles/kml/paddle/grn-blank-lv.png</href>\n");
		bw.write("\t\t</ItemIcon>\n");
		bw.write("\t</ListStyle>\n");
		bw.write("\t</Style>\n");
		bw.write("\t<Style id=\"sh_caution\">\n");
		bw.write("\t\t<IconStyle>\n");
		bw.write("\t\t\t<scale>1.2</scale>\n");
		bw.write("\t\t\t<Icon>\n");
		bw.write("\t\t\t\t<href>http://maps.google.com/mapfiles/kml/shapes/caution.png</href>\n");
		bw.write("\t\t\t</Icon>\n");
		bw.write("\t\t\t<hotSpot x=\"0.5\" y=\"0\" xunits=\"fraction\" yunits=\"fraction\"/>\n");
		bw.write("\t\t</IconStyle>\n");
		bw.write("\t\t<ListStyle>\n");
		bw.write("\t\t\t<ItemIcon>\n");
		bw.write("\t\t\t\t<href>http://maps.google.com/mapfiles/kml/paddle/grn-blank-lv.png</href>\n");
		bw.write("\t\t\t</ItemIcon>\n");
		bw.write("\t\t</ListStyle>\n");
		bw.write("\t</Style>\n");
		bw.write("\t<StyleMap id=\"msn_caution\">\n");
		bw.write("\t\t<Pair>\n");
		bw.write("\t\t\t<key>normal</key>\n");
		bw.write("\t\t\t<styleUrl>#sn_caution</styleUrl>\n");
		bw.write("\t\t</Pair>\n");
		bw.write("\t\t\t<Pair>\n");
		bw.write("\t\t\t<key>highlight</key>\n");
		bw.write("\t\t\t<styleUrl>#sh_caution</styleUrl>\n");
		bw.write("\t\t</Pair>\n");
		bw.write("\t</StyleMap>\n");

		// auszuschließende Punkte
		for (POI poi : exList) {
			bw.write("\t<Placemark>\n");
			bw.write("\t\t<name>" + poi.getName() + "</name>\n");
			bw.write("\t\t<description>von Routenplanung ausgeschlossen</description>\n");
			bw.write("\t\t<styleUrl>#msn_caution</styleUrl>\n");
			bw.write("\t\t<Point>\n");
			bw.write("\t\t\t<coordinates>" + poi.getLon() + "," + poi.getLat() + ",0</coordinates>\n");
			bw.write("\t\t</Point>\n");
			bw.write("\t</Placemark>\n");
		}

		bw.write("</Document>\n");
		bw.write("</kml>\n");
		bw.close();

		MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		MsgBox.setOutput("Die Datei \"" + Save.fileName + "\" wurde erstellt!");
		MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("----------------------------------------");
		System.out.println("Die Datei \"" + Save.fileName + "\" wurde erstellt!");
	}

	// Folgender Writer kann aus Lines und transformierten! Knoten das gesamte
	// Netz ausgeben (wird hier aber nicht benötigt).
	
	/*
	 * public static void kmlWriter2(Graph_AdjList graph, List<Line> lines,
	 * String fileName, String color) throws IOException { kmlWriter2(graph,
	 * lines, fileName, color, 1); }
	 * 
	 * public static void kmlWriter2(Graph_AdjList graph, List<Line> lines,
	 * String fileName, String color, int linewidth) throws IOException {
	 * 
	 * // Farben in einer kml-Datei: Deckkraft;Blau;Grün;Rot von 0..255 //
	 * (0..ff) String colorCode = ""; if (color.equals("red")) { colorCode =
	 * "ff0000ff"; } else if (color.equals("green")) { colorCode = "ff00ff00"; }
	 * else if (color.equals("blue")) { colorCode = "ffff0000"; } else if
	 * (color.equals("white")) { colorCode = "ffffffff"; }
	 * 
	 * FileWriter fw;
	 * 
	 * fw = new FileWriter(fileName); BufferedWriter bw = new
	 * BufferedWriter(fw);
	 * 
	 * bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	 * bw.write("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
	 * bw.write("<Document>\n"); bw.write("<name>KML-File</name>\n");
	 * 
	 * for (Line line : lines) {
	 * 
	 * bw.write("\t<Placemark>\n"); bw.write("<Style id=\"" + color + "\">\n");
	 * bw.write("<LineStyle>\n"); bw.write("<color>" + colorCode +
	 * "</color>\n"); bw.write("<width>" + linewidth + "</width>\n"); //
	 * geÃ¤ndert von 1 // auf 3 bw.write("</LineStyle>\n");
	 * bw.write("</Style>\n"); bw.write("\t\t<name>LineID: " + line.getLineId()
	 * + "</name>\n"); bw.write("\t\t<visibility>1</visibility>\n"); bw.write(
	 * "\t\t<description>Von: " + line.getNodeId1() + "; Nach: " +
	 * line.getNodeId2() + "</description>\n"); bw.write("\t\t<LineString>\n");
	 * bw.write("\t\t\t<tessellate>1</tessellate>\n");
	 * bw.write("\t\t\t<coordinates>\n");
	 * 
	 * int i1 = graph.getIndex(line.getNodeId1()); int i2 =
	 * graph.getIndex(line.getNodeId2());
	 * 
	 * bw.write("\t\t\t\t" + graph.getNodeArray()[i1].getLon() + "," +
	 * graph.getNodeArray()[i1].getLat() + ",0\n");
	 * 
	 * bw.write("\t\t\t\t" + graph.getNodeArray()[i2].getLon() + "," +
	 * graph.getNodeArray()[i2].getLat() + ",0\n");
	 * 
	 * bw.write("\t\t\t</coordinates>\n"); bw.write("\t\t</LineString>\n");
	 * bw.write("\t</Placemark>\n"); }
	 * 
	 * bw.write("</Document>\n"); bw.write("</kml>\n"); bw.close();
	 * 
	 * System.out.println("----------------------------------------");
	 * System.out.println("Die Datei \"" + fileName + "\" wurde erstellt!"); }
	 */
}

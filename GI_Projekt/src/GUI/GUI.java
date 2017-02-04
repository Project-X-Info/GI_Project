package GUI;

import java.util.ArrayList;

import javax.swing.JFrame;

import graph.POI;
import utilities.BubbleSort;

/*
 * 	author Manik Reichegger
 */

// Die Klasse GUI erstellt das äussere Fenster und ruft den Wrapper auf
public class GUI{
	public static POI[] POIListe;
	// ebthält alle Indizes der Hauptbahnhof-POIs
	public static ArrayList<Integer> HBF_index = new ArrayList<>();

	private JFrame frame;
	private Wrapper wrapper;

	public GUI(ArrayList<POI> POIList) { // dem constructor wird die POI Liste mitgegeben
		GUI.POIListe = new POI[POIList.size()];
		// for Schleife für POI die keinen Namen haben, in diesem Fall wird "Kein Name" und dessen Koordinaten in der Liste ausgegeben
		for (int c = 0; c < POIList.size(); c++) {
			POI selected = POIList.get(c);
			if (selected.getName().length() == 0) {
				selected.setName("Kein Name " + selected.getY() + "   " + selected.getX());
			}
			GUI.POIListe[c] = selected;
		}
		// Sortieren der POI-Liste (alphabetisch)
		BubbleSort bs = new BubbleSort(POIListe);
        bs.sortiere_alph_array();
        
        // Suchen des Hauptbahnhofs (mehrere POIs)
        for (int i = 0; i < POIListe.length; i++) {
			if (POIListe[i].getName().equals("Hauptbahnhof")) {
				GUI.HBF_index.add(i);
			}
			// weil sortiert --> nur bis H suchen
			if (POIListe[i].getName().startsWith("I")) {
				break;
			}
		}
        
		frame = new JFrame("Halbmarathonroutenplaner für München"); // Titel des Fensters
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// fügt close-Funktion hinzu

		wrapper = new Wrapper();
		frame.add(wrapper); // Wrapper (angepassstes JPanel) wird dem Fensster hinzugefügt
		frame.pack();
		frame.setVisible(true);		// zeigt das Fenster erst an
	}

}

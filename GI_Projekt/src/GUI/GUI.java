package GUI;

import java.util.ArrayList;

import javax.swing.JFrame;

import graph.POI;


// Die Klasse GUI erstellt das äussere Fenster und ruft den Wrapper auf
public class GUI {
	public static POI[] POIListe;

	private JFrame frame;
	private Wrapper wrapper;

	public GUI(ArrayList<POI> POIList) { // dem constructor wird die POI Liste mitgegeben
		GUI.POIListe = new POI[POIList.size()];
		for (int c = 0; c < POIList.size(); c++) {
			POI selected = POIList.get(c);
			// if Schleife f�r POI die keinen Namen haben, in diesem Fall wird "Kein Name" und dessen Koordinaten in der Liste ausgegeben
			if (selected.getName().length() == 0) {
				selected.setName("Kein Name " + selected.getY() + "   " + selected.getX());
			}
			GUI.POIListe[c] = selected;
		}
		frame = new JFrame("Marathonroutenplaner für München"); // Titel des Fensters
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// f�gt close-Funktion hinzu

		wrapper = new Wrapper();
		frame.add(wrapper); // Wrapper (angepassstes JPanel) wird dem Fensster hinzugefügt
		frame.pack();
		frame.setVisible(true);		// zeigt das Fenster erst an
	}

}

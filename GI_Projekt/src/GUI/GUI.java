package GUI;

import java.util.ArrayList;

import javax.swing.JFrame;

import graph.POI;

public class GUI {
	public static POI[] POIListe;

	private JFrame frame;
	private Wrapper wrapper;

	public GUI(ArrayList<POI> POIList) {
		GUI.POIListe = new POI[POIList.size()];
		for (int c = 0; c < POIList.size(); c++) {
			POI selected = POIList.get(c);
			if (selected.getName().length() == 0) {
				selected.setName("Kein Name " + selected.getY() + "   " + selected.getX());
			}
			GUI.POIListe[c] = selected;
		}
		frame = new JFrame("Marathonroutenplaner für München");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		wrapper = new Wrapper();
		frame.add(wrapper);
		frame.pack();
		frame.setVisible(true);
	}

}

package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import graph.POI;
import router.RouteFinder;

/*
 * 	author Manik Reichegger
 */

// In dieser Klasse wird die letzte Spalte der Benutzeroberfläche erstellt. Diese enthält nur den "Route planen"- Button

public class ButtonPanel extends JPanel { // Kindklasse von JPanel

	public ButtonPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton Routeplanen = new JButton("Route planen");
		Routeplanen.addActionListener(new listener());
		this.add(Routeplanen);

	}

	// Hier wird ein Array erstellt (sobald geklickt wird), welches den POI der
	// Startliste und die POIs der Umgehliste enthält

	private class listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// *********** Routenplanung hier aufrufen *********** (durch
			// statische Methode oder in statischer Variable)
			new MsgBox();

			RouteFinder rout = new RouteFinder(RouteFinder.getGraph());

			POI[] POIstart = Wrapper.startListe.getSelectedPOIs(); // hier
																	// werden
																	// die
																	// ausgewählten
																	// Punkte
																	// geholt.
			POI[] POIumgehen = Wrapper.umgehListe.getSelectedPOIs();

			int startId = rout.findnearNode(POIstart[0]); // Startpunkt finden

			ArrayList<POI> exList = new ArrayList<POI>();
			for (int i : GUI.HBF_index) {
				exList.add(GUI.POIListe[i]); // sollte Hauptbahnhof sein
			}

			exList.addAll(Arrays.asList(POIumgehen));

			rout.find(startId, exList);

			//

			// ***********************************
			MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			MsgBox.setOutput("Start bei " + Wrapper.startListe.getSelectedPOIs()[0].getName());
			System.out.println("Start bei " + Wrapper.startListe.getSelectedPOIs()[0].getName());
			MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			for (int i = 0; i < exList.size(); i++) {
				String selected = exList.get(i).getName();
				if (i > 0) {
					String prev = exList.get(i - 1).getName();
					if (!selected.equals(prev)) {
						MsgBox.setOutput("Umgehen von " + selected);
						System.out.println("Umgehen von " + selected);
					}
				} else
					MsgBox.setOutput("Umgehen von " + selected);
				System.out.println("Umgehen von " + selected);
			}

			MsgBox.setOutput("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			// ******************************************
		}

	}
}

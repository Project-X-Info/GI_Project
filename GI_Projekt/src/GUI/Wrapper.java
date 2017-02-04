package GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/* 
 * author Manik Reichegger
 */
public class Wrapper extends JPanel { // Kindklasse von Jpanel
	public static SelectorPanel startListe;
	public static SelectorPanel umgehListe;

	 // Mit dem Wrapper wird das endgueltige Fenster zusammengestellt. in unserem Fall handelt es sich um einen Wrapper, 
	 // der von links nach rechts verlaeuft und fuegt 3 modifizierte JPanel hinzu  
	 // 1. und 2. Spalte enthalten die Ueberschrift, die Box mit der Punktauswahl (single bzw. multiselect) und eine Karte
	 // Die 3. Spalte enthaet einen "Route planen" Knopf um die Routenplanung auszufuehren
	
	public Wrapper() {
		//POI Liste mit boolean false, da nur single selection (single selection fuer Startpunkt)
		startListe = new SelectorPanel("Startpunkt", GUI.POIListe, false);  
		umgehListe = new SelectorPanel("Gebiete die ausgeschlossen werden sollen", GUI.POIListe, true);	// POI Liste mit multiselection
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // X-Axis, da von links nach rechts

		this.add(startListe);
		this.add(umgehListe);
		this.add(new ButtonPanel());
	}

}
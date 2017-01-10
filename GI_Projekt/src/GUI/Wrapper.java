package GUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Wrapper extends JPanel {
	public static SelectorPanel startListe;
	public static SelectorPanel umgehListe;

	 // Mit dem Wrapper wird das endgültige Fenster zusammengestellt. in unserem Fall handelt es sich um einen Wrapper, 
	 // der von links nach rechts verläuft und ist in 3 Spalten aufgeteilt. 
	 // 1. und 2. Spalte enthalten die Überschrift, die Box mit der Punktauswahl (single bzw. multiselect) und eine Karte
	 // Die 3. Spalte enthät einen "Route planen" Knopf um die Routenplanung auszuführen
	
	public Wrapper() {
		//POI Liste mit boolean false, da nur single selection (single selection für Startpunkt)
		startListe = new SelectorPanel("Startpunkt", GUI.POIListe, false);  
		umgehListe = new SelectorPanel("Gebiete die ausgeschlossen werden sollen", GUI.POIListe, true);	// POI Liste mit multiselection
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(startListe);
		this.add(umgehListe);
		this.add(new ButtonPanel());
	}

}

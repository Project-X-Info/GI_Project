package GUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Wrapper extends JPanel { // Kindklasse von Jpanel
	public static SelectorPanel startListe;
	public static SelectorPanel umgehListe;

	 // Mit dem Wrapper wird das endg�ltige Fenster zusammengestellt. in unserem Fall handelt es sich um einen Wrapper, 
	 // der von links nach rechts verl�uft und fügt 3 modifizierte JPanel hinzu  
	 // 1. und 2. Spalte enthalten die �berschrift, die Box mit der Punktauswahl (single bzw. multiselect) und eine Karte
	 // Die 3. Spalte enth�t einen "Route planen" Knopf um die Routenplanung auszuf�hren
	
	public Wrapper() {
		//POI Liste mit boolean false, da nur single selection (single selection f�r Startpunkt)
		startListe = new SelectorPanel("Startpunkt", GUI.POIListe, false);  
		umgehListe = new SelectorPanel("Gebiete die ausgeschlossen werden sollen", GUI.POIListe, true);	// POI Liste mit multiselection
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // X-Axis, da von links nach rechts

		this.add(startListe);
		this.add(umgehListe);
		this.add(new ButtonPanel());
	}

}

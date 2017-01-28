package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import graph.POI;

// Mit dem Selctor Panel wird eine Spalte definiert (um nachher im Panel einzufügen). Es enth�lt einen Platzhalter f�r den Titel, f�r die Liste und die Karte
// Hier wird auch die url f�r die Karte erstellt. Hierf�r gibt es im Google Maps developer Abschnitt eine Erl�uterung wie mittels API Daten solche Karten 
// in ein Programm miteinbezogen werden k�nnen (Google static map API)

public class SelectorPanel extends JPanel { //Kindklasse von JPanel

	private JLabel title;
	private JList liste;
	private Karte karte; //Objekt von Typ Karte (JPanel)
	private URL standardkarte;
	private String defurl = "http://maps.googleapis.com/maps/api/staticmap?center=48.1403271,11.5995212&zoom=12&size=600x300&maptype=roadmap";
	private boolean multiselect;

	
	public SelectorPanel(String name, Object[] daten, boolean multiselect) {
		this.multiselect = multiselect;	// Auswahl mehrerer POI f�r die Punkte, die umgangen werden sollen
		title = new JLabel(name);
		title.setFont(new Font("Arial", Font.BOLD, 20)); // �berschrift (Schriftart, Formatierung, Gr��e)
		liste = new JList(daten);
		liste.addListSelectionListener(new Listener());
		// JScrollPane ermoeglicht scrollen in einem Fenster
		JScrollPane listScroller = new JScrollPane(liste);
		listScroller.setPreferredSize(new Dimension(600, 300)); // Box im Fenster, welche 600x300 Pixel gro� ist (scrollen m�glich falss n�tig)
		if (multiselect) {
			liste.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Mehrauswahl möglich
		} else {
			liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // nur eine Auswahl möglich (Startpunkt)
		}
		karte = new Karte(defurl);
		this.setPreferredSize(new Dimension(600, 800)); // Angabe der Boxgröße 
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);
		this.add(listScroller);
		this.add(karte);

	}
	// erstellt neue url sobald eine Auswahl stattfindet 
	protected void clicked() {
		String url = createUrl();
		// String url wird an Kartenobjekt übergeben
		karte.setUrl(url);
		
	}

	// Zusammenbau der url mittels Grundstruktur die f�r alle Punkte stimmt, den Koordinaten, dem Zoomfaktor und der Fenstergr��e
	
	private String createUrl() {
		int[] selectedIndices = liste.getSelectedIndices();

		String url = "http://maps.googleapis.com/maps/api/staticmap?center=";
		if (multiselect) {
			url = url + "48.1403271,11.5995212&zoom=12";	// Koordinaten des Standardkartenmittelpunktes (selbst gew�hlt, dass alle Punkte sichtbar sind)
			// else- Schleife f�r die single select Liste 
		} else {
			POI selected = GUI.POIListe[liste.getSelectedIndex()];
			url = url + selected.getLat() + "," + selected.getLon() + "&zoom=15";	// Zoom Faktor ist hier h�her, da nur ein Punkt angezeigt wird 
		}

		url = url + "&size=600x300&maptype=roadmap"; // Kartengr��e mit 600x300 Pixel

		for (int index : selectedIndices) {
			POI selected = GUI.POIListe[index];
			url = url + "&markers=color:red%7Clabel:%7C" + selected.getLat() + "," + selected.getLon();
		}
		return url;
	}

	// Ausgew�hlte Punkte werden zurückgegeben 
	
	public POI[] getSelectedPOIs() {
		int[] selectedIndices = liste.getSelectedIndices();
		POI[] selectedPOIs = new POI[selectedIndices.length];
		int c = 0;
		for (int index : selectedIndices) {
			selectedPOIs[c] = GUI.POIListe[index];
			c++;

		}
		return selectedPOIs;
	}

	// Wenn die Auswahl verändert wird ruft er die Methode valueChanged auf, diese ruft die Methode clicked auf
	private class Listener implements ListSelectionListener {
		public Listener() {

		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			clicked();

		}

	}

}

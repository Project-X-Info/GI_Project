package GUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Wrapper extends JPanel {
	public static SelectorPanel startListe;
	public static SelectorPanel umgehListe;

	public Wrapper() {

		startListe = new SelectorPanel("Startpunkt", GUI.POIListe, false);
		umgehListe = new SelectorPanel("Gebiete die ausgeschlossen werden sollen", GUI.POIListe, true);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(startListe);
		this.add(umgehListe);
		this.add(new ButtonPanel());
	}

}

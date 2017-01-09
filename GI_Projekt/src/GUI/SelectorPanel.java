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

public class SelectorPanel extends JPanel {

	private JLabel title;
	private JList liste;
	private Karte karte;
	private URL standardkarte;
	private String defurl = "http://maps.googleapis.com/maps/api/staticmap?center=48.1403271,11.5995212&zoom=12&size=600x300&maptype=roadmap";
	private boolean multiselect;

	public SelectorPanel(String name, Object[] daten, boolean multiselect) {
		this.multiselect = multiselect;
		title = new JLabel(name);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		liste = new JList(daten);
		liste.addListSelectionListener(new Listener());
		JScrollPane listScroller = new JScrollPane(liste);
		listScroller.setPreferredSize(new Dimension(600, 300));

		if (multiselect) {
			liste.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		} else {
			liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		karte = new Karte(defurl);
		this.setPreferredSize(new Dimension(600, 800));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(title);
		this.add(listScroller);
		this.add(karte);

	}

	protected void clicked() {
		karte.setUrl(createUrl());
		// this.repaint();
	}

	private String createUrl() {
		int[] selectedIndices = liste.getSelectedIndices();

		String url = "http://maps.googleapis.com/maps/api/staticmap?center=";
		if (multiselect) {
			url = url + "48.1403271,11.5995212&zoom=12";
		} else {
			POI selected = GUI.POIListe[liste.getSelectedIndex()];
			url = url + selected.getLat() + "," + selected.getLon() + "&zoom=15";
		}

		url = url + "&size=600x300&maptype=roadmap";

		for (int index : selectedIndices) {
			POI selected = GUI.POIListe[index];
			url = url + "&markers=color:blue%7Clabel:S%7C" + selected.getLat() + "," + selected.getLon();
		}
		return url;
	}

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

	private class Listener implements ListSelectionListener {
		// private JList
		public Listener() {

		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			clicked();

		}

	}

}

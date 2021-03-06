package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * 	author Manik Reichegger
 */

// Diese Klasse ist ein JPanel mit Hintergrundbild. Diese Hintergrundbild wird von Google Maps erstellt und kann mithilfe
// der Methode setUrl beliebig waehrend der Laufzeit angepasst werden

public class Map extends JPanel { //Kindklasse von JPanel
	private BufferedImage image;
	private String url;

	public Map(String urlx) {
		setUrl(urlx);

	}

	public void setUrl(String urlx) {
		this.url = urlx;
		try {
			image = ImageIO.read(new URL(url));
		// Erstellung einer Fehlermeldung, falls keine Internetverbindung vorliegt, da Karte sonst nicht geladen werden kann
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Bitte prüfen Sie Ihre Internetverbindung", "Verbindungsfehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}


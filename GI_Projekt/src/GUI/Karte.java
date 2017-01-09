package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Karte extends JPanel {
	private BufferedImage image;
	private String url;

	public Karte(String urlx) {
		setUrl(urlx);

	}

	public void setUrl(String urlx) {
		this.url = urlx;
		try {
			image = ImageIO.read(new URL(url));

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

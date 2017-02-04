package GUI;

import java.awt.*;
import javax.swing.*;

/*
 * 	author Michael Recla
 */

public class SplashScreen {

	JWindow window;

	public SplashScreen(String imageSrc) {
		window = new JWindow();

		// Gr��e des Fensters
		int width = 800;
		int height = 300;
		// Zentrierung des Fensters �ber ScreenSize
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		window.setBounds(x, y, width, height);

		// Das JWindow wird mit einem JLabel gef�llt, welches das Startbild, und
		// einem, welches den Schriftzug enth�lt
		JLabel label = new JLabel(new ImageIcon(this.getClass().getResource(imageSrc)));
		JLabel text = new JLabel("Geoinformatik Projektarbeit WS 16/17", JLabel.CENTER);
		text.setFont(new Font("Arial", Font.BOLD, 14));
		window.add(label, BorderLayout.CENTER);
		window.add(text, BorderLayout.SOUTH);

		// Fenster anzeigen
		window.setVisible(true);
	}

	// Fenster schlie�en
	public void close() {
		window.setVisible(false);
		window.dispose();
	}
}
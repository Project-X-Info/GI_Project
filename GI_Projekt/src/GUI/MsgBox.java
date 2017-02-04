package GUI;

import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/*
 * 	author Michael Recla
 * 
 * Soll Statusberichte über ein Fenster ausgeben (~Konsole)
 */

public class MsgBox {
	private static JFrame jFrame = new JFrame();
	public static JLabel jLabel = new JLabel();
	private static Container cont;
	// ScrollPane für Ausgaben
	public static JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	public static JScrollBar scrollbar = scrollPane.getVerticalScrollBar();
	private static String output = "";

	// Bei Aufruf wird ein Fenster erstellt
	public MsgBox() {

		// Wird in der gleichen Instanz eine neue Route berechnet, schließt sich
		// vorheriges Fenster und ein neues wird geöffnet (ohne output)
		try {
			jFrame.dispose();
		} finally {
			output = "";

			jFrame = new JFrame("Konsole");
			jFrame.setSize(700, 300);
			jFrame.setVisible(true);

			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			cont = jFrame.getContentPane();
			cont.setLayout(new BoxLayout(cont, BoxLayout.PAGE_AXIS));
			cont.setIgnoreRepaint(false);
		}

	}

	// Bei Aufruf wird der übergebene String dem output hinzugefügt
	public static void setOutput(String s) {
		// bei jeder neuen Eingabe wird der vorherige ScrollPane gelöscht und
		// ein neuer mit neuem output angelegt
		try {
			cont.remove(scrollPane);
		} finally {
			String[] s_array = s.split("\n");
			// neue Zeile innerhalb von JLabel in html-Schreibweise <p/>
			for (int i = 0; i < s_array.length; i++) {
				output = output + s_array[i] + "<p/>";
			}
			jLabel = new JLabel();

			jLabel.setText("<html>" + output + "</html>");
			jLabel.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel.setVerticalAlignment(SwingConstants.CENTER);

			scrollPane.setViewportView(jLabel);
			// ganz nach unten scrollen
			scrollbar.setValue(scrollbar.getMaximum());

			cont.add(scrollPane, Container.CENTER_ALIGNMENT);
			// um innerhalb von Schleifen die Regenerierung zu sichern, müssen
			// Container sowie JFrame andauernd neu "gezeichnet" werden
			cont.paint(cont.getGraphics());
			jFrame.revalidate();
			jFrame.paint(jFrame.getGraphics());
		}
	}
}

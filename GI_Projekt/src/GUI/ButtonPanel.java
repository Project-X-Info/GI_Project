package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import graph.POI;

public class ButtonPanel extends JPanel {
	public ButtonPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton Routeplanen = new JButton("Route planen");
		Routeplanen.addActionListener(new listener());
		this.add(Routeplanen);

	}

	private class listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			POI[] POIstart = Wrapper.startListe.getSelectedPOIs();
			POI[] POIumgehen = Wrapper.umgehListe.getSelectedPOIs();
			for (POI a : POIstart) {
				System.out.println("Start bei " + a.getName());

			}
			for (POI a : POIumgehen) {
				System.out.println("Umgehen von " + a.getName());
			}
		}

	}
}

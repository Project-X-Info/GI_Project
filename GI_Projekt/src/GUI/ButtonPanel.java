package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import graph.POI;

// In dieser Klasse wird die letzte Spalte der Benutzeroberfläche erstellt. Diese enthält nur den "Route planen"- Button

public class ButtonPanel extends JPanel { //Kindklassse von JPanel
	
	public ButtonPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		JButton Routeplanen = new JButton("Route planen");
		Routeplanen.addActionListener(new listener());
		this.add(Routeplanen);
	

	}

	//Hier wird ein Array erstellt (sobald geklickt wird), welches den POI der Startliste und die POIs der Umgehliste enthält
	
	private class listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			// *********** Routenplanung hier aufrufen *********** (durch statiss che Methode oder in statischer Variable)
			
			// ***********************************
			POI[] POIstart = Wrapper.startListe.getSelectedPOIs(); // hier werden die ausgewählten Punkte geholt.
			POI[] POIumgehen = Wrapper.umgehListe.getSelectedPOIs();
			
		
			for (POI a : POIstart) {
				System.out.println("Start bei " + a.getName());

			}
			for (POI a : POIumgehen) {
				System.out.println("Umgehen von " + a.getName());
			}
		  //******************************************
		}

	}
}

package utilities;

import java.util.*;
import graph.*;

/*
 * author Oliver Geißendörfer/Michael Recla
 * 
 * Vergleicht jeweils Nachbarn und tauscht diese bei Bedarf.
 */

public class BubbleSort {
	Node[] x;
	POI[] POIArray;
	ArrayList<POI> POIList;

	public BubbleSort(Node[] n) { // Constructor
		this.x = n;
	}

	public BubbleSort(POI[] POIArray) { // Constructor
		this.POIArray = POIArray;
	}

	public BubbleSort(ArrayList<POI> POIList) { // Constructor
		this.POIList = POIList;
	}

	public void sortiere_alph_array() {
		boolean unsortiert = true;
		POI temp;

		while (unsortiert) {
			unsortiert = false;
			for (int i = 0; i < POIArray.length - 1; i++)
				if (POIArray[i].getName().compareToIgnoreCase(POIArray[i + 1].getName()) > 0) {
					temp = POIArray[i];
					POIArray[i] = POIArray[i + 1];
					POIArray[i + 1] = temp;
					unsortiert = true;
				}
		}
	}

	public void sortiere_alph() {
		boolean unsortiert = true;
		POI temp;

		while (unsortiert) {
			unsortiert = false;
			for (int i = 0; i < POIList.size() - 1; i++)
				if (POIList.get(i).getName().compareToIgnoreCase(POIList.get(i + 1).getName()) > 0) {
					temp = POIList.get(i);
					POIList.set(i, POIList.get(i + 1));
					POIList.set((i + 1), temp);
					unsortiert = true;
				}
		}
	}

	// Wurde ursprünglich dazu verwendet, alle Knoten nach ID zu sortieren (wird
	// aber jetzt nicht weiter verwendet)

	public void sortiere() {
		boolean unsortiert = true;
		Node temp;

		while (unsortiert) {
			unsortiert = false;
			for (int i = 0; i < x.length - 1; i++)
				if (x[i].getNodeId() > x[i + 1].getNodeId()) {
					temp = x[i];
					x[i] = x[i + 1];
					x[i + 1] = temp;
					unsortiert = true;
				}
		}
	}
}

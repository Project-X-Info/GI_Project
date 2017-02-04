package utilities;

import java.util.ArrayList;
import graph.*;

/**
 * @author Son Nguyen, Tatjana Kutzner
 * @version 02.06.2016, TU München
 * angepasst auf Dijkstra MinHeap: Oliver Geißendörfer
 */
public class MinHeap {

    private ArrayList<Integer> heapListe;
    Node[] nodes;

    public int compare(int i, int j) {
        if (nodes[i].getDist2Src() < nodes[j].getDist2Src()) {
            return -1;
        } else {
            if (nodes[i].getDist2Src() > nodes[j].getDist2Src()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public ArrayList<Integer> getHeapListe() {
        return heapListe;
    }

    public void setHeapListe(ArrayList<Integer> heapListe) {
        this.heapListe = heapListe;
    }

    public MinHeap(Node[] n) {  // Constructor
        this.heapListe = new ArrayList<>();
        this.nodes = n;
        // Dummy bei Indexposition 0, da wir gemaess Vorlesung das Array erst
        // bei Indexposition 1 mit Werten fuellen moechten
        this.heapListe.add(0);
    }

    public int getSize() {
        // Abzueglich der Indexposition 0, da sich dort nur ein Dummywert
        // befindet
        return this.heapListe.size() - 1;
    }

    public void insert(int wert) {
        // Wert wird am Ende eingefuegt und steigt anschließend bis zur
        // finalen Position auf
        this.heapListe.add(wert);
        upHeap(this.heapListe.size() - 1);
    }

    // Aufsteigen des Wertes an Indexposition i
    private void upHeap(int i) {

        if (i == 0 || i == 1) {
            // Liste enthaelt maximal einen Wert
            return;
        }

        if (compare(this.heapListe.get(i),this.heapListe.get(i / 2)) < 0) {
            int tmp = this.heapListe.get(i);
            this.heapListe.set(i, this.heapListe.get(i / 2));
            this.heapListe.set(i / 2, tmp);
            upHeap(i / 2);
        }
    }

    public int deleteMin() {

        int kleinstesElement = this.heapListe.get(1);

        // Verschiebe Element von groesster Indexposition an Indexposition 1
        this.heapListe.set(1, this.heapListe.get(this.getSize()));
        this.heapListe.remove(this.getSize());

        downHeap(1);

        return kleinstesElement;
    }

    // Einsinken des Wertes an Indexposition i
    private void downHeap(int i) {

        if (i == 0 || i == this.getSize()) {
            return;
        }

        if (2 * i + 1 <= this.getSize()) {
            if ((compare(this.heapListe.get(i),this.heapListe.get(2 * i)) > 0)
                    || (compare(this.heapListe.get(i),this.heapListe.get(2 * i + 1)) > 0)) {
                // Der Knoten soll mit dem kleineren der beiden Soehne
                // vertauscht
                // werden
                if (compare(this.heapListe.get(2 * i),this.heapListe.get(2 * i + 1)) < 0) {
                    // Der linke Sohn ist kleiner als der rechte Sohn
                    int tmp = this.heapListe.get(i);
                    this.heapListe.set(i, this.heapListe.get(2 * i));
                    this.heapListe.set(2 * i, tmp);
                    downHeap(2 * i);
                } else {
                    // Der rechte Sohn ist kleiner als der linke Sohn
                    int tmp = this.heapListe.get(i);
                    this.heapListe.set(i, this.heapListe.get(2 * i + 1));
                    this.heapListe.set(2 * i + 1, tmp);
                    downHeap(2 * i + 1);
                }
            }
        } else if (2 * i <= this.getSize()) {
            // Dieser Fall tritt ein, wenn der Knoten nur einen linken Sohn in
            // der letzten Ebene hat
            if (compare(this.heapListe.get(i),this.heapListe.get(2 * i)) > 0) {
                int tmp = this.heapListe.get(i);
                this.heapListe.set(i, this.heapListe.get(2 * i));
                this.heapListe.set(2 * i, tmp);
                downHeap(2 * i);
            }
        }
    }

    // Loescht den uebergebenen Wert aus dem Array
    public int delete(int wert) {
        for (int i = 1; i <= this.getSize(); i++) {
            if (this.heapListe.get(i) == wert) {
                this.heapListe.set(i, Integer.MIN_VALUE);
                upHeap(i);
                return deleteMin();
            }
        }
        // Ist der uebergebene Wert nicht im Array vorhanden, wird -1
        // zurueckgegeben
        return -1;
    }

}

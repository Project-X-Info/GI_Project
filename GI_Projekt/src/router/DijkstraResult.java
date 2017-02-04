package router;

import java.util.*;

import adjacencylist.*;
import graph.*;
import utilities.MinHeap;

/*
 * 	author Oliver Geißendörfer
 */

public class DijkstraResult {
    Graph_AdjList graph;
    int n;
    double[] dist;
    int[] prev;

    public DijkstraResult() {
        graph = null;
        dist = null;
        prev = null;
    }


    /*
     * Darstellen bzw. speichern der Dijkstra Ergebnisse
     * Array mit minimalen Distanzen (Dist2Src) von Start Knoten zu jedem Knoten
     * Array mit dem minimal Pfad (PrevNode)
     */
    public DijkstraResult(Graph_AdjList g) {
        this.graph = g;
        Node[] knoten = graph.getNodeArray();
        this.n = knoten.length;
        dist = new double[n];
        prev = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = knoten[i].getDist2Src();
            prev[i] = knoten[i].getPrevNode();
        }
    }
    // ausgeben der Strecke vom Startknoten zu einem gewünschten Ziel
    public double mindist(int dest) {
        return dist[dest];
    }

    /*
     * ausgeben einer ArrayList,
     * die alle Knoten welche auf dem Pfad zu einem Zielknoten liegen
     * beinhaltet.
     */
    public ArrayList<Node> minpath(int dest) {
        Node[] knoten = graph.getNodeArray();
        ArrayList<Node> nodeList = new ArrayList<Node>();
        if (prev[dest] != -1) {
            nodeList.add(knoten[dest]);
            for (int k = prev[dest]; k != -1; k = prev[k]) {
                nodeList.add(knoten[k]);
            }
        }
        return nodeList;
    }
}
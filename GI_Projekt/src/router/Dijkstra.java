package router;

import java.util.*;

import adjacencylist.*;
import graph.*;
import router.*;
import utilities.MinHeap;

/*
 * 	author Oliver Geißendörfer
 */

public class Dijkstra {

    static boolean debug = false;
    static boolean useHeap = true; // Minimalknotensuche mit heap (zu empfehlen)
    Graph_AdjList graph;


    public Dijkstra() {
        this.graph = null;
    }

    public Dijkstra(Graph_AdjList g) {
        this.graph = g;
    }

    public void setGraph(Graph_AdjList g) {
        this.graph = g;
    }

    // Funktion um die Ergebnisse vom Dijkstra auszugeben
    private void printSolution(int n, Node[] knoten) {
        System.out.println("[Idx] Id     Dist from Src       backwards path from Source to Target vertex");
        for (int i = 0; i < n; i++) {
            if (knoten[i].getPrevNode() != -1) {
                System.out.print("["+i+"] "+knoten[i].getNodeId()+" \t\t "+knoten[i].getDist2Src());
                System.out.print(" \t\t "+knoten[i].getNodeId());
                for (int k = knoten[i].getPrevNode(); k != -1; k = knoten[k].getPrevNode()) {
                    System.out.print(" <- "+knoten[k].getNodeId());
                }
                System.out.println(" ");
            } else {
                System.out.println("["+i+"] "+knoten[i].getNodeId()+" \t\t not reachable!");
            }
        }
    }

    public void printSolution() {
        Node[] knoten = graph.getNodeArray();
        int n = knoten.length;
        printSolution(n,knoten);
    }

    /*
     * Funktion, die die minimale Distanz
     * von einem Startknoten zu allen Knoten ausgibt
     */
    public DijkstraResult dijkstra(int src) {

        int count = 0;
        int u,v;
        MinHeap heap;
        long start = 0, end = 0;

        Node[] knoten = graph.getNodeArray();
        NodeList[] adjlist = graph.getAdjacencyList();

        int n = knoten.length;


        if (debug) {
            start = new Date().getTime();
            System.out.println("Dijkstra Start ...");
        }

        heap = new MinHeap(knoten);

        // Alle Distanzen sind unendlich und alle Knoten sind unbesucht (visited = false)
        for (int i = 0; i < n; i++) {
            knoten[i].setDist2Src(Double.MAX_VALUE);
            knoten[i].setPrevNode(-1);
            knoten[i].setVisited(false);
        }

        // Entfernung vom Startknoten zum Startknoten muss immer = 0 sein
        knoten[src].setDist2Src(0);
        if (useHeap) {
            heap.insert(src);
        }

        // Finden der minimalen Distanz mit einem minHeap (deutlich effizienter)
        while ((useHeap && (heap.getSize() > 0)) || (!useHeap && (++count)  < n)) {

            if (debug) {
                if (count%1000 == 0) System.out.println("Dijkstra Durchlauf "+count+" von insgesamt "+(n-1)+" Zyklen");
            } else {
                if (!useHeap) {
                    if (count%1000 == 0) System.out.print(".");
                }
            }

            if (useHeap) {
                u = heap.deleteMin();
            } else {
                double min = Double.MAX_VALUE;
                u = -1;

                for (int w = 0; w < n; w++) {
                    if (knoten[w].getVisited() == false && knoten[w].getDist2Src() <= min) {
                        min = knoten[w].getDist2Src();
                        u = w;
                    }
                }
            }

            // Knoten mit gefundener minimal Distanz als besucht setzen (visited = true)
            knoten[u].setVisited(true);

            NodeElem nachbarn = adjlist[u].getHead();

            // suche des naechsten Nachbarn mit der kuerzesten Entfernung
            while (nachbarn != null) {
                Node nachbar = nachbarn.getValue();
                v = nachbar.getIndex();
                if ((!knoten[v].getVisited()) && (!knoten[v].gethide())
                        && knoten[u].getDist2Src()+nachbarn.getDistance() < knoten[v].getDist2Src()) {
                    knoten[v].setDist2Src(knoten[u].getDist2Src() + nachbarn.getDistance());
                    if (useHeap) {
                        heap.insert(v);
                    }
                    knoten[v].setPrevNode(u);
                }
                nachbarn = nachbarn.getNext();
            }
        }

        if (debug) {
            end = new Date().getTime()-start;
            System.out.println("\n... Dijkstra Ende time: "+end);
        }

        if (debug) {
            System.out.println("Dijkstra Solution Start");
            printSolution(n, knoten); // print the constructed distance array
            System.out.println("... Dijkstra Solution Ende");
        }

        return new DijkstraResult(graph);
    }
}



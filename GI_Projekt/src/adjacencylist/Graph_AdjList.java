package adjacencylist;

import java.util.*;
import graph.*;

public class Graph_AdjList {

	// Array aller Punkte
	public Node[] nodeArray;
	// Array aller Punkte, Index entspricht der nodeId (Zwischenräume null)
	public Node[] nodeArray_id;
	// Adjazenzliste als Array von Listen
	private NodeList[] adjacencyList;

	/*
	 * Für ihre anschließende Befüllung wird das Grundgerüst der Adjazenzliste
	 * errichtet. D. h. für jeden Knoten wird eine Liste für dessen Nachbarn
	 * bereitgestellt.
	 */
	public void initAdjacencyList() {
		int nodeNumber = nodeArray_id.length;
		adjacencyList = new NodeList[nodeNumber]; // Array aus Listen
		for (int i = 0; i < nodeNumber; i++) {
			if (nodeArray_id[i] != null) {
				adjacencyList[i] = new NodeList();// Liste für Nachbarn von
													// Knoten i
			}
		}
		System.out.println("Adajzenzliste wurde initialisiert!");
	}

	/*
	 * Mit setLines() werden in der Adjazenzliste die Listen der Nachbarn
	 * gefüllt. Jede Kante mit 'node1' und 'node2' enthält die IDs der Knoten,
	 * welche miteinander verbunden sind. Damit können diese Nachbarn aus dem
	 * nodeArray_id entnommen werden (Id und Index stimmen hier überein).
	 */
	public void setLines(List<Line> lines) {
		long start = new Date().getTime();

		System.out.println("Kanten werden gesetzt...");
		for (Line Line : lines) {
			int from = Line.getNodeId1();
			int to = Line.getNodeId2();
			NodeList fromList = adjacencyList[from];
			fromList.add(nodeArray_id[to]);
			// Graph nicht gerichtet --> auch umgekehrt anfügen
			NodeList toList = adjacencyList[to];
			toList.add(nodeArray_id[from]);
		}

		// Die Löcher (=null) in der Adjazenzliste werden nun gelöscht:
		// Alle Elemente werden in eine ArrayList überführt und später wieder in
		// ein Array rückgewandelt.
		ArrayList<NodeList> tmp_adj = new ArrayList<NodeList>();
		ArrayList<Node> tmp_nodes = new ArrayList<Node>();
		for (int i = 0; i < adjacencyList.length; i++) {
			if (adjacencyList[i] != null) {
				tmp_adj.add(adjacencyList[i]);
				tmp_nodes.add(nodeArray_id[i]);
			}
		}
		adjacencyList = tmp_adj.toArray(new NodeList[tmp_adj.size()]);
		nodeArray = tmp_nodes.toArray(new Node[tmp_nodes.size()]);

		long end = new Date().getTime() - start;
		System.out.println("line set time: " + end);
		System.out.println("Kanten sind gesetzt!");
	}

	/*
	 * Durch die Verwendung des nodeArray_id kann auf folgende Methoden
	 * verzichtet werden. Über das Array darauf zuzugreifen, ist viel
	 * effizienter.
	 */
	public Node getNodefromID(int nodeID) {
		Node k = null;
		for (int i = 0; i < nodeArray.length; i++) {
			if (nodeArray[i].getNodeId() == nodeID) {
				k = nodeArray[i];
				break;
			}
		}
		if (k == null) {
			System.out.println("Der gesuchte Node ist nicht enthalten!");
		}
		return k;
	}

	public int getIndex(int nodeID) {
		int index = -1;
		for (int i = 0; i < nodeArray.length; i++) {
			if (nodeArray[i].getNodeId() == nodeID) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			System.out.println("Der gesuchte Node ist nicht enthalten!");
		}
		return index;
	}

	private boolean allVisited() {
		boolean allVisited = true;
		for (int i = 0; i < nodeArray.length; i++) {
			if (!nodeArray[i].getVisited()) {
				return false;
			}
		}
		return allVisited;
	}
	
	// Getter und Setter

	public void setNodeArray_id(List<Node> nodeList) {
		nodeArray_id = new Node[Node.getMaxId() + 1];
		for (Node node : nodeList) {
			nodeArray_id[node.getNodeId()] = node;
		}
	}

	public Node[] getNodeArray_id() {
		return nodeArray_id;
	}

	public void setNodeArray_id(Node[] nodeArray_id) {
		this.nodeArray_id = nodeArray_id;
	}

	public Node[] getNodeArray() {
		return nodeArray;
	}

	public void setNodeArray(Node[] nodeArray) {
		this.nodeArray = nodeArray;
	}

	public NodeList[] getAdjacencyList() {
		return adjacencyList;
	}
	
	// Print-Methoden für die Konsole

	public void printNodeArray() {
		System.out.println("----------------------------------------");
		for (int i = 0; i < nodeArray.length; i++) {
			System.out.println(nodeArray[i].getNodeId() + "\t" + nodeArray[i].getX() + "\t" + nodeArray[i].getY());
		}
	}

	public void printAdjacencylist() {
		System.out.println("----------------------------------------");
		System.out.println("Index\tHead\t\tNeighbours...");
		System.out.println("----------------------------------------");
		for (int i = 0; i < adjacencyList.length; i++) {
			System.out.print(i + ":\t" + nodeArray[i].getNodeId());
			adjacencyList[i].printElements();
			System.out.println();
		}
	}

}

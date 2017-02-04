package adjacencylist;

import graph.Node;
/*
 * 	author Michael Recla
 */
public class NodeElem {

    private Node value;
    private double dist_to_neighbour;
    private NodeElem next;
    private NodeElem back;

    public NodeElem(Node k) {
        value = k;
        next = null;
    }

    public NodeElem(Node k, double dist) {
        value = k;
        dist_to_neighbour = dist;
        next = null;
    }

    public Node getValue() {
        return value;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    public double getDistance() {
        return dist_to_neighbour;
    }

    public void setDistance(double dist) {
        this.dist_to_neighbour = dist;
    }
    public NodeElem getNext() {
        return next;
    }

    public void setNext(NodeElem next) {
        this.next = next;
    }

    public NodeElem getBack() {
        return back;
    }

    public void setBack(NodeElem back) {
        this.back = back;
    }

}

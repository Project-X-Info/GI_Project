package adjacencylist;

import graph.Node;

public class NodeElem {

	private Node value;
	private NodeElem next;
	private NodeElem back;

	public NodeElem(Node k) {
		value = k;
		next = null;
	}

	public Node getValue() {
		return value;
	}

	public void setValue(Node value) {
		this.value = value;
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

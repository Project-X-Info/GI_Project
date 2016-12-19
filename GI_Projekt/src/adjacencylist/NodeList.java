package adjacencylist;

import graph.Node;

public class NodeList {

	private NodeElem head, tail;

	public NodeList() {
		head = null;
		tail = null;
	}

	public void add(Node k) {
		if (k == null) {
			return;
		}
		NodeElem newElem = new NodeElem(k);
		if (tail != null) {
			tail.setNext(newElem);
			newElem.setBack(tail);
			tail = newElem;
		} else {
			head = newElem;
			tail = newElem;
		}
	}

	public NodeElem getHead() {
		return head;
	}

	public NodeElem getElement(int index) {
		NodeElem i = head;
		for (int j = 0; j < index; j++) {
			i = i.getNext();
		}
		return i;
	}

	public int getSize() {
		return getSize(head);
	}

	private int getSize(NodeElem element) {
		if (element == null) {
			return 0;
		}
		int count = 0;
		while (element != null) {
			count++;
			element = element.getNext();
		}
		return count;
	}

	public boolean isEmpty() {
		boolean isEmpty = false;
		if (head == null && tail == null) {
			isEmpty = false;
		}
		return isEmpty;
	}

	public void printElements() {
		NodeElem k = head;
		while (k != null) {
			System.out.print("\t-->\t" + k.getValue().getNodeId());
			k = k.getNext();
		}
	}

	public NodeElem getNotVisitedNodes() {
		NodeElem k = head;
		while (k != null) {
			if (!k.getValue().getVisited()) {
				return k;
			} else {
				k = k.getNext();
			}
		}
		return k;
	}
}

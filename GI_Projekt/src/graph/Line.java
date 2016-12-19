package graph;

public class Line {
	private int lineId;
	private int nodeId1;
	private int nodeId2;

	public Line(int lineId, int nodeId1, int nodeId2) {
		this.lineId = lineId;
		this.nodeId1 = nodeId1;
		this.nodeId2 = nodeId2;
	}

	public int getLineId() {
		return lineId;
	}

	public int getNodeId1() {
		return nodeId1;
	}

	public int getNodeId2() {
		return nodeId2;
	}
	
}
package graph;

public class Node {
	private double y;
	private double x;
	private double lon;
	private double lat;
	private int nodeId;
	private int index;
	private boolean visited;
	private static int maxId;

	public Node(int nodeId, double y, double x, int index) {
		this.y = y;
		this.x = x;
		this.nodeId = nodeId;
		this.index = index;
		visited = false;
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getNodeId() {
		return nodeId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public Boolean getVisited() {
		return visited;
	}

	public static int getMaxId() {
		return maxId;
	}

	public static void setMaxId(int maxId) {
		Node.maxId = maxId;
	}
	
}

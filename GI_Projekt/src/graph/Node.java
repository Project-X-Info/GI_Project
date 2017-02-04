package graph;

/*
 * 	author Gabriel Danzl
 */

public class Node  {
    private double y;
    private double x;
    private double lon;
    private double lat;
    private int nodeId;
    private int index;
    private static int maxId; // fuer nodeArray_id
    private boolean visited;
    private boolean hide; // fuer Routenplanung
    // in AdjaListe jeweils die Strecke zum vorherigen Knoten
    private double dist_to_src;
    private int prevNode;
    
    public Node() {
    	
    }

    public Node(int nodeId, double y, double x, int index) {
        this.y = y;
        this.x = x;
        this.nodeId = nodeId;
        this.index = index;
        visited = false;
        hide = false;
        prevNode = -1;
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
    
	public static int getMaxId() {
		return maxId;
	}

	public static void setMaxId(int maxId) {
		Node.maxId = maxId;
	}

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void sethide(Boolean h) {
        this.hide = h;
    }

    public Boolean gethide() {
        return this.hide;
    }

    public void setDist2Src(double dsrc) {
        this.dist_to_src = dsrc;
    }

    public double getDist2Src() {
        return dist_to_src;
    }

    public int getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(int pindex) {
        this.prevNode = pindex;
    }

}

package graph;

public class POI {
	private int pointId;
	private String name;
	private String type;
	private double y;
	private double x;
	private double lon;
	private double lat;

	public POI(int pointId, String name, String type, double y, double x) {
		this.pointId = pointId;
		this.name = name;
		this.type = type;
		this.y = y;
		this.x = x;
	}

	public int getPointId() {
		return pointId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
	this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
	
	public String toString() {
		return this.name;
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
}
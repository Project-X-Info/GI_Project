package datamanagement;

import java.util.*;

import graph.*;

public class CoordTraf {

	public static void setLonLat(List<Node> nodeList) {

		System.out.println("Koordinaten werden transformiert...");
		long start = new Date().getTime();
		// Koordinatentransformation
		// Transformation nach Bessel 1841
		double a = 6377397.155;
		double b = 6356078.965;
		double e = (Math.pow(a, 2) - Math.pow(b, 2)) / (Math.pow(a, 2));
		double r = (Math.pow(a, 2) - Math.pow(b, 2)) / (Math.pow(b, 2));
		double n = (a - b) / (a + b);

		ArrayList<Double> rw = new ArrayList<Double>();
		ArrayList<Double> hw = new ArrayList<Double>();
		double y, tmp = 0;

		for (Node node : nodeList) {
			tmp = node.getY();
			y = tmp - 500000 - 4 * 1000000;
			rw.add(y);
			hw.add(node.getX());
		}

		ArrayList<Double> las = new ArrayList<Double>();
		ArrayList<Double> laf = new ArrayList<Double>();

		for (Double hw_tmp : hw) {
			double Las = hw_tmp * ((1 + n) / a) * (1 / (1 + 0.25 * Math.pow(n, 2) + 0.015625 * Math.pow(n, 4)));
			double Laf = Las + 1.5 * n * (1 - 0.5625 * Math.pow(n, 2)) * (Math.sin(2 * Las))
					+ 0.0625 * Math.pow(n, 2) * (21 - 27.5 * Math.pow(n, 2)) * (Math.sin(4 * Las))
					+ 1.572916667 * Math.pow(n, 3) * (Math.sin(6 * Las));
			las.add(Las);
			laf.add(Laf);
		}

		// Parameter
		ArrayList<Double> ys = new ArrayList<Double>();

		for (Double rw_tmp : rw) {
			double Ys = (rw_tmp * b) / (1 * Math.pow(a, 2));
			ys.add(Ys);
		}

		ArrayList<Double> vf = new ArrayList<Double>();
		ArrayList<Double> nf = new ArrayList<Double>();

		for (Double laf_tmp : laf) {
			double Vf = Math.sqrt(1 + r * Math.pow(Math.cos(laf_tmp), 2));
			vf.add(Vf);

			double Nf = Math.sqrt(r * (Math.pow(Math.cos(laf_tmp), 2)));
			nf.add(Nf);
		}

		double Lo0 = (4 * 3 * Math.PI) / 180;
		ArrayList<Double> l = new ArrayList<Double>();

		for (int i = 0; i < laf.size(); i++) {
			double Lt = (vf.get(i) / (Math.cos(laf.get(i)))) * Math.sinh(ys.get(i))
					* (1 - (1 / 6) * Math.pow(nf.get(i), 4) * Math.pow(ys.get(i), 2)
							- (1 / 10) * r * Math.pow(ys.get(i), 4));
			double L = Math.atan(Lt);
			l.add(L);
		}

		ArrayList<Double> lo = new ArrayList<Double>();

		for (Double l_tmp : l) {
			double Lo = Lo0 + l_tmp;
			lo.add(Lo);
		}

		ArrayList<Double> la = new ArrayList<Double>();
		for (int i = 0; i < laf.size(); i++) {
			double Lat = Math.tan(laf.get(i)) * Math.cos(vf.get(i) * l.get(i))
					* (1 - (1 / 6) * Math.pow(nf.get(i), 2) * Math.pow(l.get(i), 4));
			double La = Math.atan(Lat);
			la.add(La);

		}
		//// Übergang ins kartesisch geozentrische Koordinatensystem
		ArrayList<Double> N = new ArrayList<Double>();
		double H = 0;
		for (int i = 0; i < la.size(); i++) {
			double Nt = (Math.pow(a, 2)) / (Math.sqrt((Math.pow(a, 2)) * (Math.pow(Math.cos(la.get(i)), 2))
					+ (Math.pow(b, 2)) * (Math.pow(Math.sin(la.get(i)), 2))));
			N.add(Nt);

		}
		ArrayList<Double> X = new ArrayList<Double>();
		ArrayList<Double> Y = new ArrayList<Double>();
		ArrayList<Double> Z = new ArrayList<Double>();
		for (int i = 0; i < la.size(); i++) {
			double Xt = (N.get(i) + H) * (Math.cos(la.get(i))) * (Math.cos(lo.get(i)));
			double Yt = (N.get(i) + H) * Math.cos(la.get(i)) * Math.sin(lo.get(i));
			double Zt = (N.get(i) * (1 - e) + H) * (Math.sin(la.get(i)));
			X.add(Xt);
			Y.add(Yt);
			Z.add(Zt);
		}

		///////////////////////////////////////////////////////////////////
		/// WGS84
		double aw = 6378137.000;
		double bw = 6356752.314;
		double ew = (Math.pow(aw, 2) - Math.pow(bw, 2)) / (Math.pow(aw, 2));
		double rwgs = (Math.pow(aw, 2) - Math.pow(bw, 2)) / (Math.pow(bw, 2));

		///////////////////////////////////////////////////
		double rx = 1.04 * (Math.PI / 648000);
		double ry = 0.35 * (Math.PI / 648000);
		double rz = -3.08 * (Math.PI / 648000);
		double s = 8.3 * Math.pow(10, -6);
		double cx = 582;
		double cy = 105;
		double cz = 414;

		ArrayList<Double> Xb = new ArrayList<Double>();
		ArrayList<Double> Yb = new ArrayList<Double>();
		ArrayList<Double> Zb = new ArrayList<Double>();
		for (int i = 0; i < X.size(); i++) {
			double XB = cx + (1 + s) * (X.get(i) - rz * Y.get(i) + ry * Z.get(i));
			double YB = cy + (1 + s) * (rz * X.get(i) + Y.get(i) - rx * Z.get(i));
			double ZB = cz + (1 + s) * (-ry * X.get(i) + rx * Y.get(i) + Z.get(i));
			Xb.add(XB);
			Yb.add(YB);
			Zb.add(ZB);
		}

		/// Schritt 4 WGS 84 -> La,Lo,H (WGS 84)
		ArrayList<Double> Lo = new ArrayList<Double>();
		for (int i = 0; i < X.size(); i++) {
			double lo0 = Math.atan(Yb.get(i) / Xb.get(i));
			lo0 = (lo0 / (2 * (Math.PI))) * 360;
			// System.out.println(lo0);
			Lo.add(lo0);

		}
		ArrayList<Double> p = new ArrayList<Double>();
		for (int i = 0; i < Xb.size(); i++) {
			double P = Math.sqrt(Math.pow(Xb.get(i), 2) + Math.pow(Yb.get(i), 2));
			p.add(P);
		}
		ArrayList<Double> phi = new ArrayList<Double>();
		for (int i = 0; i < Xb.size(); i++) {
			double Phi = Math.atan((Zb.get(i) * aw) / (p.get(i) * bw));
			phi.add(Phi);
		}
		ArrayList<Double> La = new ArrayList<Double>();
		for (int i = 0; i < Xb.size(); i++) {
			double LA = Math.atan((Zb.get(i) + rwgs * bw * (Math.pow(Math.sin(phi.get(i)), 3)))
					/ (p.get(i) - ew * aw * (Math.pow(Math.cos(phi.get(i)), 3))));
			LA = LA / (2 * Math.PI) * 360;
			La.add(LA);
			// System.out.println(LA);
		}

		for (int i = 0; i < nodeList.size(); i++) {
			nodeList.get(i).setLat(La.get(i));
			nodeList.get(i).setLon(Lo.get(i));
		}

		long runningTime = new Date().getTime() - start;
		System.out.println("transformation time: " + runningTime);
		System.out.println("Koordinaten wurden transformiert!");
	}
	
	public static void printCoord(List<Node> nodeList) {
		System.out.println("--------------------------------");
		System.out.println("ID\tLatitude\tLongitude");
		for (Node node : nodeList) {
			System.out.println(node.getNodeId() + "\t" + node.getLat() + "\t" + node.getLon());
		}
	}
}

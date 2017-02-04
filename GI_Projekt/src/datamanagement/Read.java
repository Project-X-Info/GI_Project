package datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import org.jdom2.Element;

import graph.*;

/*
 * 	author Gabriel Danzl
 */

public class Read {

    public static void readNodes(String filename, ArrayList<Node> nodeList) {
        Document document = null;
        int maxId = 0;
        long start = new Date().getTime();
        try {
            document = new SAXBuilder().build(Read.class.getResource(filename));
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime()-start;
        System.out.println("nodes read time: "+end);

        Element root = document.getRootElement();
        // Namespace "gml" auslesen
        Namespace gmlNs = root.getNamespace();
        // alle Kindelemente "featureMember" auslesen
        List<Element> children = root.getChildren("featureMember",gmlNs);
        // Namespace "fme" auslesen (aus Element "Roads_Munich_Route_Node")
        Namespace fmeNs = children.get(0).getChildren().get(0).getNamespace();

        // Iteration �ber alle Kindelemente "featureMember"
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i).getChild("Roads_Munich_Route_Node",fmeNs);
            Element subchild = child.getChild("NODE_ID",fmeNs);
            int nodeId = Integer.parseInt(subchild.getText());
            if (nodeId > maxId) {
				maxId = nodeId;
			}
            subchild = child.getChild("pointProperty",gmlNs).getChild("Point",gmlNs).getChild("pos",gmlNs);
            String pos = subchild.getText();
            String[] posSplit = pos.split(" ");
            double y = Double.parseDouble(posSplit[0]);
            double x = Double.parseDouble(posSplit[1]);

            Node node = new Node(nodeId,y,x,i);
            nodeList.add(node);
        }
        Node.setMaxId(maxId);
    }

    public static void readLines(String filename, ArrayList<Line> lineList) {
        Document document = null;
        long start = new Date().getTime();
        try {
            document = new SAXBuilder().build(Read.class.getResource(filename));
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime()-start;
        System.out.println("lines read time: "+end);

        Element root = document.getRootElement();
        // Namespace "gml" auslesen
        Namespace gmlNs = root.getNamespace();
        // alle Kindelemente "featureMember" auslesen
        List<Element> children = root.getChildren("featureMember",gmlNs);
        // Namespace "fme" auslesen (aus Element "Roads_Munich_Route_Line")
        Namespace fmeNs = children.get(0).getChildren().get(0).getNamespace();

        // Iteration �ber alle Kindelemente "featureMember"
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i).getChild("Roads_Munich_Route_Line",fmeNs);
            Element subchild = child.getChild("LINE_ID",fmeNs);
            int lineId = Integer.parseInt(subchild.getText());
            subchild = child.getChild("NODE_1",fmeNs);
            int nodeId1 = Integer.parseInt(subchild.getText());
            subchild = child.getChild("NODE_2",fmeNs);
            int nodeId2 = Integer.parseInt(subchild.getText());

            Line line = new Line(lineId,nodeId1,nodeId2);
            lineList.add(line);
        }

    }

    public static void readPOIs(String filename, ArrayList<POI> POIList) {
        Document document = null;
        long start = new Date().getTime();
        try {
            document = new SAXBuilder().build(Read.class.getResource(filename));
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime()-start;
        System.out.println("POIs read time: "+end);

        Element root = document.getRootElement();
        // Namespace "gml" auslesen
        Namespace gmlNs = root.getNamespace();
        // alle Kindelemente "featureMember" auslesen
        List<Element> children = root.getChildren("featureMember",gmlNs);
        // Namespace "fme" auslesen (aus Element "Roads_Munich_Route_POIs")
        Namespace fmeNs = children.get(0).getChildren().get(0).getNamespace();

        // Iteration �ber alle Kindelemente "featureMember"
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i).getChild("Roads_Munich_Route_POIs",fmeNs);
            Element subchild = child.getChild("name",fmeNs);
            String name = subchild.getText();
            subchild = child.getChild("type",fmeNs);
            String type = subchild.getText();
            subchild = child.getChild("POINT_ID",fmeNs);
            int pointId = Integer.parseInt(subchild.getText());

            subchild = child.getChild("pointProperty",gmlNs).getChild("Point",gmlNs).getChild("pos",gmlNs);
            String pos = subchild.getText();
            String[] posSplit = pos.split(" ");
            double y = Double.parseDouble(posSplit[0]);
            double x = Double.parseDouble(posSplit[1]);

            POI poi = new POI(pointId,name,type,y,x);
            POIList.add(poi);
        }

    }
}

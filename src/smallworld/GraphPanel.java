/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JPanel;

/**
 *
 * @author alewis19
 */
public class GraphPanel extends JPanel {

    private Graph graph;
    private double radius = .05;
    private double outerRadius=radius*4;
    private TreeMap<String, LocationNode> locations;
    private ArrayList<String> lines;

    public GraphPanel(Graph g) {
        graph = g;
        locations = new TreeMap<>();
        lines = new ArrayList<>(graph.V());
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int w = this.getWidth();
        int h = this.getHeight();
        AffineTransform scale = new AffineTransform();
        AffineTransform translate = new AffineTransform();
        AffineTransform transform = new AffineTransform();
        translate.setToTranslation(1, 1);
        scale.setToScale(w / 2, h / 2);
        transform.concatenate(scale);
        transform.concatenate(translate);
        this.circlePaint();
        Set<String> keys=locations.keySet();
        for(String key: keys) {
            LocationNode n=locations.get(key);
            Shape e=new Ellipse2D.Double(n.getX(),n.getY(),radius,radius);
            Shape s=transform.createTransformedShape(e);
            g2d.fill(s);
        }
        for(String line:lines) {
            GeneralPath path = new GeneralPath();
            String[] points=line.split("/");
            String [] p0=points[0].split(":");
            String [] p1=points[1].split(":");
            Shape e = new Line2D.Double(parseDouble(p0[0]),parseDouble(p0[1]),parseDouble(p1[0]),parseDouble(p1[1]));
            Shape s=transform.createTransformedShape(e);
            g2d.draw(s);
        }
    }

    public void circlePaint() {
        ArrayList<String> inLocs = new ArrayList<>(graph.V());
        double center = 0;
        double neg = 0;
        int size=inLocs.size();
        while (size<=graph.V()) {
            Iterable<String> keys = graph.vertices();
            int max = -1;
            String maxKey = "";
            for (String key : keys) {
                if (graph.degree(key) > max && !inLocs.contains(maxKey)) {
                    maxKey = key;
                    max = graph.degree(key);
                }
            }
            String centerPoint;
            LocationNode loc;
            if (neg % 2 == 0) {
                loc = new LocationNode(center+radius, center+radius);
                center += .1;
                centerPoint = center + ":" + center;
            } else {
                double newCenter = center * -1;
                loc = new LocationNode(newCenter+radius, newCenter+radius);
                centerPoint = newCenter + ":" + newCenter;
            }
            neg++;
            locations.put(maxKey, loc);
            inLocs.add(maxKey);
            //System.out.println(maxKey);
            Iterable<String> links = graph.adjacentTo(maxKey);
            double angle = (2 * Math.PI) / max;
            for (String link : links) {
                String otherLoc = "";
                if (inLocs.contains(link)) {
                    Set<String> k = locations.keySet();
                    for (String key : k) {
                        if (key.equals(link)) {
                            LocationNode locNode = locations.get(key);
                            otherLoc = locNode.getX() + ":" + locNode.getY();
                        }
                    }
                } else {
                    double c;
                    if (neg % 2 != 0) {
                        c = center * -1;
                    } else {
                        c = center;
                    }
                    double x = c + outerRadius * Math.cos(angle);
                    double y = c + outerRadius * Math.sin(angle);
                    locations.put(link, new LocationNode(x, y));
                    inLocs.add(link);
                    otherLoc = x + ":" + y;
                }
                String line = centerPoint + "/" + otherLoc;
                lines.add(line);
                angle+=angle;
                if(angle>Math.PI*2) {
                    angle=Math.PI*2;
                }
            }
            size=inLocs.size();
        }
    }
}

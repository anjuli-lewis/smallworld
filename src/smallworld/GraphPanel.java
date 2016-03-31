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

    private final Graph graph;
    private final double radius = .05;
    private final double outerRadius=.5;
    private TreeMap<String, LocationNode> locations;
    private ArrayList<String> lines;
    private int size=0;

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
        size=0;
        while (size<graph.V()) {
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
                loc = new LocationNode(center, center);
                centerPoint = center + ":" + center;
            } else {
                double newCenter = center * -1;
                loc = new LocationNode(newCenter, newCenter);
                centerPoint = newCenter + ":" + newCenter;
            }
            neg++;
            center += .1;
            locations.put(maxKey, loc);
            inLocs.add(maxKey);
            circleWithCenter(maxKey);
            
            size++;
        }
//        ArrayList<String> nodes=new ArrayList<>(graph.V());
//        Iterable<String> keys=graph.vertices();
//        for(String key: keys) {
//            nodes.add(key);
//        }
//        circleNoCenter(nodes, nodes.size());
    }
    private void circleWithCenter(String key) {
        LocationNode loc=locations.get(key);
        Iterable<String> nodes=graph.adjacentTo(key);
        double angle=0;
        double angleIncrease=360/graph.degree(key);
        double x;
        double y;
        LocationNode n;
        for(String node:nodes) {
            if(!locations.containsKey(node)) {
                do {
                    x = loc.getX() + outerRadius * Math.cos(angle);
                    y = loc.getY() + outerRadius * Math.sin(angle);
                    angle+=angleIncrease;
                    n=new LocationNode(x,y);
                } while(locations.containsValue(n));
                locations.put(node,n);
                size++;
            }
            else {
                n=locations.get(node);
            }
            String p0=loc.getX()+":"+loc.getY();
            String p1=n.getX()+":"+n.getY();
            String line=p0+"/"+p1;
            lines.add(line);
        }
    }
    
    private void circleNoCenter(ArrayList<String> nodes) {
        LocationNode loc=new LocationNode(0,0);
        double angle=0;
        double angleIncrease=360/nodes.size();
        double x;
        double y;
        LocationNode n;
        for(String node:nodes) {
            if(!locations.containsKey(node)) {
                do {
                    x = loc.getX() + outerRadius * Math.cos(angle);
                    y = loc.getY() + outerRadius * Math.sin(angle);
                    System.out.println(angle);
                    angle+=angleIncrease;
                    n=new LocationNode(x,y);
                } while(locations.containsValue(n));
                locations.put(node,n);
                size++;
            }
            else {
                n=locations.get(node);
            }
            /*String p0=loc.getX()+":"+loc.getY();
            String p1=n.getX()+":"+n.getY();
            String line=p0+"/"+p1;
            lines.add(line);*/
        }
    }
    
    
}

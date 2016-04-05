/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JPanel;

/**
 * Draws the graph by finding the node with the most connections, placing that in 
 * the center and then placing all the nodes equal distances from the center node
 * in a circle, then it goes to each of the nodes connected to the center node, 
 * and if that node has more one connection, treats that as another center node.
 * If there is no center node, it places all the nodes with equal connections in
 * a circle centered at the origin.
 * @author alewis19
 */
public class GraphPanel extends JPanel {

    private final Graph graph; /** the graph to be displayed */
    private double diameter; /** the diameter of the circles */
    private final double outerRadius; /** the radius of the outer circle */
    private TreeMap<String, Location> locations; /** the locations of all the circle*/
    private ArrayList<String> lines; /** the start and end points for all the lines */
    private final String origin; /** the start point for a highlighted path */
    private final String destination; /** the end point for a highlighted path */
    private final Color pathColor = new Color(0, 13, 206); /** the color of a path */
    private final Color connectColor = new Color(25, 25, 25); /** 
     * the color of a connection not a part of the path */

    /**
     * Creates a Graph panel with a origin and destination for the path finder
     * @param g the Graph to be used in this panel
     * @param origin
     * @param destination 
     */
    public GraphPanel(Graph g, String origin, String destination) {
        graph = g;
        locations = new TreeMap<>();
        lines = new ArrayList<>(graph.V());
        double s = g.E();
        diameter = (s % 10) / 100;
        System.out.println(s);
        if (diameter == 0) {
            diameter = 0.1;
        }
        outerRadius = diameter * 5;
        this.origin = origin;
        this.destination = destination;
    }
/**
 * Creates a GraphPanel that will not highlight a set path
 * @param g 
 */
    public GraphPanel(Graph g) {
        graph = g;
        locations = new TreeMap<>();
        lines = new ArrayList<>(graph.V());
        double s = g.E();
        diameter = (s % 10) / 100;
        outerRadius = diameter * 5;
        this.origin = null;
        this.destination = null;
    }
/**
 * Draws the graph
 * @param g 
 */
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
        Set<String> keys = locations.keySet();
        for (String key : keys) {
            Location n = locations.get(key);
            Shape e = new Ellipse2D.Double(n.getX() - diameter / 2, n.getY() - diameter / 2, diameter, diameter);
            Shape s = transform.createTransformedShape(e);
            g2d.fill(s);
        }
        this.generateLines();
        ArrayList<String> path = new ArrayList<>();
        if (origin != null) {
            PathFinder p = new PathFinder(graph, origin);
            for (String s : p.pathTo(destination)) {
                path.add(s);
            }
            path.trimToSize();
        }
        for (String line : lines) {
            String[] points = line.split("/");
            String[] p0 = points[0].split(":");
            String[] p1 = points[1].split(":");
            double x1 = parseDouble(p0[0]);
            double y1 = parseDouble(p0[1]);
            double x2 = parseDouble(p1[0]);
            double y2 = parseDouble(p1[1]);
            if (origin != null) {
                for (int i = 0; i < path.size() - 1; i++) {
                    String stop = path.get(i);
                    String nextStop = path.get(i + 1);
                    if ((locations.get(stop).atPoint(x1, y1) || locations.get(stop).atPoint(x2, y2))
                            && (locations.get(nextStop).atPoint(x1, y1) || locations.get(nextStop).atPoint(x2, y2))) {
                        g2d.setColor(pathColor);
                        break;
                    }
                }
            }
            Shape e = new Line2D.Double(x1, y1, x2, y2);
            Shape s = transform.createTransformedShape(e);
            g2d.draw(s);
            g2d.setColor(connectColor);
        }
    }
/**
 * Figures out if the graph has a center point or not and passes to the appropiate method
 */
    private void circlePaint() {
        ArrayList<String> inLocs = new ArrayList<>(graph.V());
        double center = 0;
        double neg = 0;
        Iterable<String> keys = graph.vertices();
        ArrayList<String> nodes = new ArrayList<>();
        int max = -1;
        String maxKey = "";
        for (String key : keys) {
            if (graph.degree(key) > max && !inLocs.contains(maxKey)) {
                maxKey = key;
                max = graph.degree(key);
            }
            if (graph.degree(key) == max && !nodes.contains(key)) {
                nodes.add(key);
            }
        }
        if (nodes.size() > 1) {
            circleNoCenter(nodes);
        } else {
            Location loc = new Location(0, 0);
            locations.put(maxKey, loc);
            circleWithCenter(maxKey);
        }
    }
/**
 * Generates one layer of the graph from the given center node. Goes through each 
 * node, treating it as a center node
 * @param key 
 */
    private void circleWithCenter(String key) {
        if (locations.size() != graph.V()) {
            Location loc = locations.get(key);
            Iterable<String> nodes = graph.adjacentTo(key);
            double angle = 0;
            double angleIncrease = (Math.PI * 2) / graph.degree(key);
            double x;
            double y;
            Location n;
            for (String node : nodes) {
                if (!locations.containsKey(node)) {
                    do {
                        x = loc.getX() + outerRadius * Math.cos(angle);
                        y = loc.getY() + outerRadius * Math.sin(angle);
                        angle += angleIncrease;
                        n = new Location(x, y);
                    } while ((x < 1-diameter/2) && (x > -1) && (y > 1 - diameter/2) && (y < -1) && !locations.containsValue(n));
                    locations.put(node, n);
                }
            }
            for (String node : nodes) {
                if (graph.degree(node) > 1) {
                    for (String s : graph.adjacentTo(node)) {
                        if (!locations.containsKey(s)) {
                            this.circleWithCenter(node);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void circleNoCenter(ArrayList<String> nodes) {
        Location loc = new Location(0, 0);
        double angle = 0;
        double angleIncrease = (Math.PI * 2) / nodes.size();
        double x;
        double y;
        Location n;
        for (String node : nodes) {
            if (!locations.containsKey(node)) {
                do {
                    x = outerRadius * Math.cos(angle);
                    y = outerRadius * Math.sin(angle);
                    angle += angleIncrease;
                    n = new Location(x, y);
                } while ((x > 1 - diameter) && (x < -1) && (y > 1 - diameter) && (y < -1) && !locations.containsValue(n));
                locations.put(node, n);
            }
        }
        for (String node : nodes) {
            if (graph.degree(node) > 1) {
                for (String s : graph.adjacentTo(node)) {
                    if (!locations.containsKey(s)) {
                        this.circleWithCenter(node);
                        break;
                    }
                }
            }
        }
    }

    private void generateLines() {
        Set<String> keys = locations.keySet();
        for (String key : keys) {
            Iterable<String> adjacent = graph.adjacentTo(key);
            for (String a : adjacent) {
                Location p0 = locations.get(key);
                Location p1 = locations.get(a);
                String line = p0.getX() + ":" + p0.getY() + "/" + p1.getX() + ":" + p1.getY();
                String line2 = p1.getX() + ":" + p1.getY() + "/" + p0.getX() + ":" + p0.getY();
                if (!lines.contains(line) && !lines.contains(line2)) {
                    lines.add(line);
                }
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
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
public class GraphPanel extends JPanel implements MouseListener{

    private final Graph graph;
    private final double radius = .05;
    private final double outerRadius=.5;
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
            Shape e=new Ellipse2D.Double(n.getX()-radius/2,n.getY()-radius/2,radius,radius);
            Shape s=transform.createTransformedShape(e);
            g2d.fill(s);
        }
        generateLines();
        for(String line:lines) {
            String[] points=line.split("/");
            String [] p0=points[0].split(":");
            String [] p1=points[1].split(":");
            Shape e = new Line2D.Double(parseDouble(p0[0]),parseDouble(p0[1]),parseDouble(p1[0]),parseDouble(p1[1]));
            Shape s=transform.createTransformedShape(e);
            g2d.draw(s);
        }
    }

    private void circlePaint() {
        ArrayList<String> inLocs = new ArrayList<>(graph.V());
        double center = 0;
        double neg = 0;
            Iterable<String> keys = graph.vertices();
            ArrayList<String> nodes=new ArrayList<>();
            int max = -1;
            String maxKey = "";
            for (String key : keys) {
                if (graph.degree(key) > max && !inLocs.contains(maxKey)) {
                    maxKey = key;
                    max = graph.degree(key);
                }
                if(graph.degree(key)==max && !nodes.contains(key)) {
                    nodes.add(key);
                }
            }
            if(nodes.size()>1) {
                circleNoCenter(nodes);
            }
            else {
                LocationNode loc=new LocationNode(0,0);
                locations.put(maxKey,loc);
                circleWithCenter(maxKey);
            }
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
                } while((x>1-radius*2)||(x<-1)||(y>1-radius*2)||(y<-1)||locations.containsValue(n));
                locations.put(node,n);
            }
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
                    angle+=angleIncrease;
                    n=new LocationNode(x,y);
                } while(x>1||x<-1||y>1||y<-1||locations.containsValue(n));
                locations.put(node,n);
            }
        }
        for(String node:nodes) {
            circleWithCenter(node);
        }
    }
    private void generateLines() {
        Set<String> keys=locations.keySet();
        for(String key:keys) {
            Iterable<String> adjacent=graph.adjacentTo(key);
            for(String a:adjacent) {
                LocationNode p0=locations.get(key);
                LocationNode p1=locations.get(a);
                String line=p0.getX()+":"+p0.getY()+"/"+p1.getX()+":"+p1.getY();
                String line2=p1.getX()+":"+p1.getY()+"/"+p0.getX()+":"+p0.getY();
                if(!lines.contains(line)&&!lines.contains(line2)) {
                    lines.add(line);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Set<String> keys=locations.keySet();
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+", "+y);
        for(String key:keys) {
            LocationNode n=locations.get(key);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author alewis19
 */
public class GraphFrame extends JFrame {

    private Timer timer;
    public static final int FRAME_WIDTH = 768;
    public static final int FRAME_HEIGHT = 768;
    private int time;
    
    public GraphFrame(Graph g) {
        this.time = time;
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = this.getContentPane();
        GraphPanel panel = new GraphPanel(g);
        pane.add(panel);
        timer.start();
        this.setVisible(true);

    }
    public static void main(String[] args) {
        Graph G = new Graph();
        G.addEdge("A", "B");
        G.addEdge("A", "C");
        G.addEdge("C", "D");
        G.addEdge("D", "E");
        G.addEdge("D", "G");
        G.addEdge("E", "G");
        G.addVertex("H");
        GraphFrame g=new GraphFrame(G);
    }
}

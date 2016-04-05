/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author alewis19
 */
public class GraphFrame extends JFrame {

    public static final int FRAME_WIDTH = 768;
    public static final int FRAME_HEIGHT = 768;
    
    public GraphFrame(Graph g) {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = this.getContentPane();
        GraphPanel panel = new GraphPanel(g);
        pane.add(panel);
        this.setVisible(true);

    }
    public static void main(String[] args) {
        Graph G = new Graph();
        //two centers
//        G.addEdge("A", "B");
//        G.addEdge("A", "C");
//        G.addEdge("F", "D");
//        G.addEdge("F", "E");
        //one center
        G.addEdge("A", "B");
        G.addEdge("A", "C");
        G.addEdge("A", "D");
        G.addEdge("A", "E");
        G.addEdge("A", "F");
        G.addEdge("A", "G");
        G.addEdge("B", "H");
        //no center
//        G.addEdge("A", "B");
//        G.addEdge("B", "C");
//        G.addEdge("C", "D");
//        G.addEdge("D", "E");
//        G.addEdge("E", "F");
//        G.addEdge("F", "G");
        GraphFrame g=new GraphFrame(G);
    }
}

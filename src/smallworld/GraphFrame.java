/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

import edu.princeton.cs.In;
import java.awt.Container;
import javax.swing.JFrame;

/**
 * Holds the GraphPanel and displays the graph
 * @author alewis19
 */
public class GraphFrame extends JFrame {

    public static final int FRAME_WIDTH = 768;
    public static final int FRAME_HEIGHT = 768;
/**
 * Creates a new instance of the GraphFrane, sets it to be visible and adds a Graph panel with the given graph
 * @param g the graph to be displayed
 */
    public GraphFrame(Graph g) {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = this.getContentPane();
        GraphPanel panel = new GraphPanel(g, "A", "B");
        pane.add(panel);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        Graph G = new Graph();
//        two centers
//        G.addEdge("A", "B");
//        G.addEdge("A", "C");
//        G.addEdge("F", "B");
//        G.addEdge("F", "C");
//        G.addEdge("K", "A");
//        G.addEdge("F", "Z");
//        G.addEdge("M", "A");
//        G.addEdge("F", "T");
//        G.addEdge("U", "A");
//        G.addEdge("F", "V");
//        G.addEdge("Y", "V");
//        one center
//        G.addEdge("A", "B");
//        G.addEdge("A", "C");
//        G.addEdge("A", "D");
//        G.addEdge("A", "E");
//        G.addEdge("A", "F");
//        G.addEdge("A", "G");
//        G.addEdge("B", "H");
//        G.addEdge("B","C");
//        G.addEdge("B","D");
//        G.addEdge("D","F");
//        G.addEdge("D","Z");
//        G.addEdge("D","K");
//        G.addEdge("Z","K");
//        G.addEdge("K","F");
//        G.addEdge("G","L");
        //no center
//        G.addEdge("A", "B");
//        G.addEdge("A", "C");
//        G.addEdge("A", "D");
//        G.addEdge("A", "E");
//        G.addEdge("A", "F");
//        G.addEdge("B", "C");
//        G.addEdge("B", "D");
//        G.addEdge("B", "E");
//        G.addEdge("B", "F");
//        G.addEdge("C", "D");
//        G.addEdge("C", "E");
//        G.addEdge("C", "F");
//        G.addEdge("D", "E");
//        G.addEdge("D", "F");
//        G.addEdge("E", "F");
        //orgho
        G.addEdge("A","B");
        G.addEdge("A","C");
        G.addEdge("A","D");
        G.addEdge("A","E");
        G.addEdge("B","F");
        G.addEdge("B","G");
        G.addEdge("B","H");
        G.addEdge("B","I");
        G.addEdge("C","J");
        G.addEdge("C","K");
        G.addEdge("C","L");
        G.addEdge("C","M");
        G.addEdge("D","N");
        G.addEdge("D","O");
        G.addEdge("D","P");
        G.addEdge("D","Q");
        G.addEdge("E","R");
        G.addEdge("E","S");
        G.addEdge("E","T");
        G.addEdge("E","U");
        GraphFrame g = new GraphFrame(G);
    }
}

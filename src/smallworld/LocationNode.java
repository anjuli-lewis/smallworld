/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smallworld;

/**
 *
 * @author anjul_000
 */
public class LocationNode {
    private double x;
    private double y;
    
    public LocationNode(double x,double y) {
        this.x=x;
        this.y=y;
    }
   /**
    * returns whether it is at the point specified
    * @param x x value of the other point
    * @param y y value of the other point
    * @return boolean whether the x and y are equal
    */
    public boolean atPoint(double x, double y) {
        return (this.getX()==x&&this.getY()==y);
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }
    
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
    
}

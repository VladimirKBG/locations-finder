
package com.naumen_contest.locationsfinder.Service.Model;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class Location {
    private static int count = 0;
    
    private final long id;
    private final double x;
    private final double y;
    

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = count++;
    }
    
    public double squareDistanceTo(Location other) {
        double dx = x - other.X();
        double dy = y - other.Y();
        return Math.pow(dx, 2) + Math.pow(dy,2);
    }

    public double X() {
        return x;
    }

    public double Y() {
        return y;
    }

    public long Id() {
        return id;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }
    
}

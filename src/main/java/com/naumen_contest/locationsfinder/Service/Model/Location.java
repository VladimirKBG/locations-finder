
package com.naumen_contest.locationsfinder.Service.Model;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class Location {
    private static int count = 0;
    
    private final int id;
    private final double x;
    private final double y;
    

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = count++;
    }

    public double X() {
        return x;
    }

    public double Y() {
        return y;
    }

    public int Id() {
        return id;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }
    
}

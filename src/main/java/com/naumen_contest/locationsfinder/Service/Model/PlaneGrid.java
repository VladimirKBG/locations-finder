
package com.naumen_contest.locationsfinder.Service.Model;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class PlaneGrid {
    private final List<List<List<Location>>> grid;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private double dx;
    private double dy;
    private final int sizeX;
    private final int sizeY;

    public PlaneGrid(LocationsDTOWithR dto) {
        List<Location> locs = dto.getLocations();
        double R = dto.getRadius();
        xMin = Double.MAX_VALUE; xMax = Double.MIN_VALUE; yMin = Double.MAX_VALUE; yMax = Double.MIN_VALUE;
        for (Location loc : locs) {
            xMax = loc.X() > xMax ? loc.X() : xMax;
            xMin = loc.X() < xMin ? loc.X() : xMin;
            yMax = loc.Y() > yMax ? loc.Y() : yMax;
            yMin = loc.Y() < yMin ? loc.Y() : yMin;
        }
        
        int gridSizeX = (int) ((xMax - xMin)/R);
        int gridSizeY = (int) ((yMax - yMin)/R);
        sizeX = gridSizeX + 3;
        sizeY = gridSizeY + 3;
        dx = (xMax - xMin)/Math.max(gridSizeX,1);
        dy = (yMax - yMin)/Math.max(gridSizeY,1);
        dx = dx > 0 ? dx : Double.MAX_VALUE;
        dy = dy > 0 ? dy : Double.MAX_VALUE;

        grid = new ArrayList<>(sizeX);
        for (int i = 0; i < sizeX; i++) {
            List<List<Location>> column = new ArrayList<>();
            for (int j = 0; j < sizeY + 3; j++) {
                column.add(new ArrayList<>());
            }
            grid.add(column);
        }  
    }
    
    public void addEntity(Location loc, double x, double y) {
        int xRow = _getXRow(x);
        int yRow = _getYRow(y);
        grid.get(xRow).get(yRow).add(loc);
    }
    
    public List<List<Location>> getNearestCells(double x, double y) {
        int xRow = _getXRow(x);
        int yRow = _getYRow(y);
        int startX = xRow > 0 ? xRow - 1 : 0;
        int startY = yRow > 0 ? yRow - 1 : 0;
        int endX = xRow < sizeX - 1 ? xRow + 1 : sizeX - 1;
        int endY = yRow < sizeY - 1 ? yRow + 1 : sizeY - 1;
        List<List<Location>> res = new ArrayList<>(9);
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                res.add(grid.get(i).get(j));
            }
        }
        return res;
    }
    
    private int _getXRow(double x) {
        return (int) ((x - xMin)/dx + 1);
    }
    
    private int _getYRow(double y) {
        return (int) ((y - yMin)/dy + 1);
    }
}

package com.naumen_contest.locationsfinder.Service.Dto;

import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.util.List;

/**
 *
 * @author user
 */
public class InputLocationsDto implements LocationsDto {
    private final double radius;
    private final List<Location> locations;

    public InputLocationsDto(int N, double radius, List<Location> locations) {
        if (radius <= 0)
            throw new IllegalArgumentException("Radius must be > 0");
        if (N < 0)
            throw new IllegalArgumentException("Locations count must be non-negative");
        if (N != locations.size())
            throw new IllegalArgumentException(
                    "Wrong number of Locations was read: %s, but %s was expected "
                            + "from headers line.".formatted(locations.size(), N));
        this.radius = radius;
        this.locations = locations;
    }
    
    public double getRadius() {
        return radius;
    }

    public int getSize() {
        return locations.size();
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "InputDataContainer{" + "radius=" + radius + ", locations=" + locations + '}';
    }
    
}

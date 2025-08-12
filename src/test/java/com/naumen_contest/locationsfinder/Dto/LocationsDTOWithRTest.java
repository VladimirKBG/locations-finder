
package com.naumen_contest.locationsfinder.Dto;

import com.naumen_contest.locationsfinder.Model.Location;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsDTOWithRTest {
    
    double delta = 1e-6;
    List<Location> sampleLocations = List.of(
            new Location(0,0),
            new Location(10,20)
            );
    
    @Test
    void testConstructor_sampleDTO() {
        double radius = 10.0;
        List<Location> locations = sampleLocations;
        int N = locations.size();
        
        LocationsDTOWithR dto = new LocationsDTOWithR(N, radius, locations);
        
        assertEquals(radius, dto.getRadius(), delta);
        assertEquals(N, dto.getSize());
        assertEquals(locations, dto.getLocations());
    }

    @Test
    void testConstructor_sampleDTO_zeroR() {
        double radius = 0.0;
        List<Location> locations = sampleLocations;
        int N = locations.size();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new LocationsDTOWithR(N, radius, locations);
        });
    }
    
    @Test
    void testConstructor_sampleDTO_negativeR() {
        double radius = -10.0;
        List<Location> locations = sampleLocations;
        int N = locations.size();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new LocationsDTOWithR(N, radius, locations);
        });
    }
    
    @Test
    void testConstructor_sampleDTO_negativeN() {
        double radius = 10.0;
        List<Location> locations = sampleLocations;
        int N = -1;
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new LocationsDTOWithR(N, radius, locations);
        });
    }
    
    @Test
    void testConstructor_sampleDTO_missmatchN() {
        double radius = 10.0;
        List<Location> locations = sampleLocations;
        int N = locations.size();
        
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new LocationsDTOWithR(N-1, radius, locations);
        });
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new LocationsDTOWithR(N+1, radius, locations);
        });
    }
}

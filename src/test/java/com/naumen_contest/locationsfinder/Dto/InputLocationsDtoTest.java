
package com.naumen_contest.locationsfinder.Dto;

import com.naumen_contest.locationsfinder.Model.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class InputLocationsDtoTest {
    
    @Test
    void ZeroDataTest() {
        int N = 0;
        double R = 0.1d;
        List<Location> locations = new ArrayList<>(0);
        LocationsDTOWithR idc = new LocationsDTOWithR(N, R, locations);
        assertEquals(N, idc.getSize());
        assertEquals(R, idc.getRadius());
        assertEquals(locations, idc.getLocations());
    }
    
    @Test
    void FixedDataTest() {
        int N = 5;
        double R = 0.1d;
        List<Location> locations = new ArrayList<>(List.of(
                new Location(1,1),
                new Location(-2,1),
                new Location(2,-3),
                new Location(-4,3),
                new Location(4,-5)
        ));
        LocationsDTOWithR idc = new LocationsDTOWithR(N, R, locations);
        assertEquals(N, idc.getSize());
        assertEquals(R, idc.getRadius());
        assertEquals(locations, idc.getLocations());
    }
    
    @Test
    void RandomTest() {
        int testCount = 100;
        int N;
        double R;
        List<Location> locations;
        LocationsDTOWithR idc;
        for (int i = 0; i < testCount; i++) {
            N = ThreadLocalRandom.current().nextInt(1, 10000000);
            R = ThreadLocalRandom.current().nextDouble(1.0e-100, 1.7e100);
            locations = new ArrayList<>(N);
            for (int j = 0; j < N; j++) {
                double x = ThreadLocalRandom.current().nextDouble(-1.7e100, 1.7e100);
                double y = ThreadLocalRandom.current().nextDouble(-1.7e100, 1.7e100);
                locations.add(new Location(x, y));
            }
            idc = new LocationsDTOWithR(N, R, locations);
            assertEquals(N, idc.getSize());
            assertEquals(R, idc.getRadius());
            assertEquals(locations, idc.getLocations());
        }
    }
}

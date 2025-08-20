
package com.naumen_contest.locationsfinder.Strategy;

import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Model.Location;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class N2NeighborsCountingStrategyTest {
    private N2NeighborsCountingStrategy strategy;
    
    @BeforeEach
    void setup() {
        strategy = new N2NeighborsCountingStrategy();
    }
    
    private Location mockLocation(long id) {
        Location loc = Mockito.mock(Location.class);
        Mockito.when(loc.Id()).thenReturn(id);
        return loc;
    }
    
    private void setSquareDistance(Location from, Location to, double distance) {
        Mockito.when(from.squareDistanceTo(to)).thenReturn(distance);
    }
    
    private LocationsDTOWithR dtoWith(List<Location> locations, double radius) {
        LocationsDTOWithR dto = Mockito.mock(LocationsDTOWithR.class);
        Mockito.when(dto.getLocations()).thenReturn(locations);
        Mockito.when(dto.getRadius()).thenReturn(radius);
        return dto;
    }
    
    @Test
    void countNeighborsTest_emptyList() {
        LocationsDTOWithR dto = dtoWith(Collections.emptyList(), 1.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertNotNull(result, "Result map should not be null");
        assertTrue(result.isEmpty(), "Empty input locations should produce an empty map");
    }
    
    @Test
    void countNeighborsTest_singleLocation() {
        Location loc = mockLocation(1L);
        LocationsDTOWithR dto = dtoWith(Collections.singletonList(loc), 5.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertEquals(1, result.size(), "Map must contain exactly one entry for single location");
        assertEquals(0L, result.get(1L).longValue(), "Single location should have zero neighbors");
    }
    
    @Test
    void countNeighborsTest_twoWithinRadius() {
        Location a = mockLocation(1L);
        Location b = mockLocation(2L);

        setSquareDistance(a, b, 9.0);
        setSquareDistance(b, a, 9.0);

        LocationsDTOWithR dto = dtoWith(Arrays.asList(a, b), 5.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertEquals(2, result.size(), "Two locations -> map should have two entries");
        assertEquals(1L, result.get(1L).longValue(), "Location 1 should have 1 neighbor");
        assertEquals(1L, result.get(2L).longValue(), "Location 2 should have 1 neighbor");
    }
    
    @Test
    void countNeighborsTest_twoOutsideRadius() {
        Location a = mockLocation(1L);
        Location b = mockLocation(2L);

        setSquareDistance(a, b, 100.0);
        setSquareDistance(b, a, 100.0);

        LocationsDTOWithR dto = dtoWith(Arrays.asList(a, b), 3.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertEquals(2, result.size(), "Two locations -> map should have two entries");
        assertEquals(0L, result.get(1L).longValue(), "Location 1 should have 0 neighbors when outside radius");
        assertEquals(0L, result.get(2L).longValue(), "Location 2 should have 0 neighbors when outside radius");
    }
    
    @Test
    void countNeighborsTest_distanceEqualToRadius() {
        Location a = mockLocation(1L);
        Location b = mockLocation(2L);

        setSquareDistance(a, b, 9.0);
        setSquareDistance(b, a, 9.0);

        LocationsDTOWithR dto = dtoWith(Arrays.asList(a, b), 3.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertEquals(1L, result.get(1L).longValue(), "Distance equal to radius should be considered inside (<= R)");
        assertEquals(1L, result.get(2L).longValue(), "Distance equal to radius should be considered inside (<= R)");
    }
    
    @Test
    void countNeighborsTest_multipleNeighbors() {
        Location a = mockLocation(1L);
        Location b = mockLocation(2L);
        Location c = mockLocation(3L);


        setSquareDistance(a, b, 4.0);
        setSquareDistance(b, a, 4.0);

        setSquareDistance(a, c, 16.0);
        setSquareDistance(c, a, 16.0);

        setSquareDistance(b, c, 100.0);
        setSquareDistance(c, b, 100.0);

        LocationsDTOWithR dto = dtoWith(Arrays.asList(a, b, c), 5.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertEquals(3, result.size(), "Three locations -> map should have three entries");
        assertEquals(2L, result.get(1L).longValue(), "Location A should have two neighbors (B and C)");
        assertEquals(1L, result.get(2L).longValue(), "Location B should have one neighbor (A)");
        assertEquals(1L, result.get(3L).longValue(), "Location C should have one neighbor (A)");
    }
    
    @Test
    void countNeighborsTest_zeroRadiusAndZeroDistance() {
        Location a = mockLocation(1L);
        Location b = mockLocation(2L);

        // radius 0 -> R = 0; squared distance 0 => should be counted because <= R
        setSquareDistance(a, b, 0.0);
        setSquareDistance(b, a, 0.0);

        LocationsDTOWithR dto = dtoWith(Arrays.asList(a, b), 0.0);

        Map<Long, Long> result = strategy.countNeighbors(dto);

        assertEquals(1L, result.get(1L).longValue(), "When radius is zero and squared distance is zero, count should be 1");
        assertEquals(1L, result.get(2L).longValue(), "When radius is zero and squared distance is zero, count should be 1");
    }
}

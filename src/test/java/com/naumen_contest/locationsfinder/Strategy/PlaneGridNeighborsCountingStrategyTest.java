
package com.naumen_contest.locationsfinder.Strategy;

import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Model.Location;
import com.naumen_contest.locationsfinder.Model.PlaneGrid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class PlaneGridNeighborsCountingStrategyTest {

    @Mock
    private PlaneGrid mockPlaneGrid;

    @Mock
    private LocationsDTOWithR mockInputDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    private List<Location> createLocations(double[]... coords) {
        return List.of(
            new Location(coords[0][0], coords[0][1]),
            new Location(coords[1][0], coords[1][1]),
            new Location(coords[2][0], coords[2][1]),
            new Location(coords[3][0], coords[3][1])
        );
    }
    
    @Test
    void testCountNeighbors_oneNeighborInEachPoint() {
        double radius = 10.0;
        List<Location> locs = createLocations(
            new double[]{0, 0}, new double[]{1, 1},
            new double[]{100, 100}, new double[]{101, 101}
        );

        when(mockInputDto.getLocations()).thenReturn(locs);
        when(mockInputDto.getRadius()).thenReturn(radius);

        when(mockPlaneGrid.getNearestCells(locs.get(0).X(), locs.get(0).Y()))
            .thenReturn(List.of(Collections.emptyList()));
        when(mockPlaneGrid.getNearestCells(locs.get(1).X(), locs.get(1).Y()))
            .thenReturn(List.of(List.of(locs.get(0))));
        when(mockPlaneGrid.getNearestCells(locs.get(2).X(), locs.get(2).Y()))
            .thenReturn(List.of(Collections.emptyList()));
        when(mockPlaneGrid.getNearestCells(locs.get(3).X(), locs.get(3).Y()))
            .thenReturn(List.of(List.of(locs.get(2))));

        PlaneGridNeighborsCountingStrategy strategy = new PlaneGridNeighborsCountingStrategy(mockPlaneGrid);
        Map<Long, Long> score = strategy.countNeighbors(mockInputDto);

        assertEquals(1L, score.get(locs.get(0).Id()));
        assertEquals(1L, score.get(locs.get(1).Id()));
        assertEquals(1L, score.get(locs.get(2).Id()));
        assertEquals(1L, score.get(locs.get(3).Id()));

        verify(mockPlaneGrid, times(1)).initializePlaneGrid(mockInputDto);
        verify(mockPlaneGrid, times(locs.size())).getNearestCells(anyDouble(), anyDouble());
        verify(mockPlaneGrid, times(locs.size())).addEntity(any(), anyDouble(), anyDouble());
    }

    @Test
    void testCountNeighbors_noNeighbors() {
        double radius = 1.0;
        List<Location> locs = createLocations(
            new double[]{0, 0}, new double[]{10, 10},
            new double[]{20, 20}, new double[]{30, 30}
        );

        when(mockInputDto.getLocations()).thenReturn(locs);
        when(mockInputDto.getRadius()).thenReturn(radius);

        when(mockPlaneGrid.getNearestCells(anyDouble(), anyDouble()))
            .thenReturn(List.of(Collections.emptyList()));
            
        PlaneGridNeighborsCountingStrategy strategy = new PlaneGridNeighborsCountingStrategy(mockPlaneGrid);
        Map<Long, Long> score = strategy.countNeighbors(mockInputDto);

        assertEquals(0L, score.get(locs.get(0).Id()));
        assertEquals(0L, score.get(locs.get(1).Id()));
        assertEquals(0L, score.get(locs.get(2).Id()));
        assertEquals(0L, score.get(locs.get(3).Id()));


        verify(mockPlaneGrid, times(locs.size())).getNearestCells(anyDouble(), anyDouble());
    }
    
    @Test
    void testCountNeighbors_emptyLocations_returnsEmptyMap() {
        when(mockInputDto.getLocations()).thenReturn(Collections.emptyList());
        when(mockInputDto.getRadius()).thenReturn(10.0);
        
        PlaneGridNeighborsCountingStrategy strategy = new PlaneGridNeighborsCountingStrategy(mockPlaneGrid);
        Map<Long, Long> score = strategy.countNeighbors(mockInputDto);

        assertTrue(score.isEmpty());
        
        verify(mockPlaneGrid, times(1)).initializePlaneGrid(mockInputDto);
        verify(mockPlaneGrid, never()).getNearestCells(anyDouble(), anyDouble());
        verify(mockPlaneGrid, never()).addEntity(any(), anyDouble(), anyDouble());
    }
}

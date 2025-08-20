
package com.naumen_contest.locationsfinder.Service;

import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Parser.LocationsParserWithR;
import com.naumen_contest.locationsfinder.Strategy.NeighborsCountingStrategy;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsAppriserByNeighborsCountTest {
    private static final String N2_NAME = "n2NeighborsCountingStrategy";
    private static final String PLANE_NAME = "planeGridNeighborsCountingStrategy";
    
    private LocationsDTOWithR mockDtoWithSize(int size) {
        LocationsDTOWithR dto = Mockito.mock(LocationsDTOWithR.class);
        Mockito.when(dto.getSize()).thenReturn(size);
        return dto;
    }
    
    private LocationsAppriserByNeighborsCount createAppriser(LocationsParserWithR parser, Map<String, NeighborsCountingStrategy> strategies) {
        return new LocationsAppriserByNeighborsCount(parser, strategies);
    }
    
    private List<String> someRawData() {
        return Arrays.asList("line1", "line2");
    }
    
    private Map<String, NeighborsCountingStrategy> strategiesMap(Map.Entry<String, NeighborsCountingStrategy>... entries) {
        Map<String, NeighborsCountingStrategy> map = new HashMap<>();
        for (Map.Entry<String, NeighborsCountingStrategy> e : entries) {
            map.put(e.getKey(), e.getValue());
        }
        return map;
    }
    
    @Test
    void appriseLocationsTest_usesN2_whenSizeEquals10000() throws Exception {
        LocationsParserWithR parser = Mockito.mock(LocationsParserWithR.class);
        NeighborsCountingStrategy n2 = Mockito.mock(NeighborsCountingStrategy.class);

        Map<String, NeighborsCountingStrategy> strategies = strategiesMap(
                Map.entry(N2_NAME, n2)
        );

        LocationsDTOWithR dto = mockDtoWithSize(10000);
        List<String> raw = someRawData();

        Mockito.when(parser.parseData(raw)).thenReturn(dto);

        Map<Long, Long> expected = Collections.singletonMap(1L, 42L);
        Mockito.when(n2.countNeighbors(dto)).thenReturn(expected);

        LocationsAppriserByNeighborsCount appriser = createAppriser(parser, strategies);

        Map<Long, Long> result = appriser.appriseLocations(raw);

        assertSame(expected, result, "Returned map should be the exact map produced by the n2 strategy");
        Mockito.verify(parser, Mockito.times(1)).parseData(raw);
        Mockito.verify(n2, Mockito.times(1)).countNeighbors(dto);
    }
    
    @Test
    void appriseLocationsTest_usesN2_whenSizeLessThan10000() throws Exception {
        LocationsParserWithR parser = Mockito.mock(LocationsParserWithR.class);
        NeighborsCountingStrategy n2 = Mockito.mock(NeighborsCountingStrategy.class);
        NeighborsCountingStrategy plane = Mockito.mock(NeighborsCountingStrategy.class);
        Map<String, NeighborsCountingStrategy> strategies = strategiesMap(
                Map.entry(N2_NAME, n2),
                Map.entry(PLANE_NAME, plane)
        );

        LocationsDTOWithR dto = mockDtoWithSize(5);
        List<String> raw = someRawData();

        Mockito.when(parser.parseData(raw)).thenReturn(dto);

        Map<Long, Long> expected = Collections.singletonMap(7L, 7L);
        Mockito.when(n2.countNeighbors(dto)).thenReturn(expected);

        LocationsAppriserByNeighborsCount appriser = createAppriser(parser, strategies);

        Map<Long, Long> result = appriser.appriseLocations(raw);

        assertSame(expected, result, "Returned map should be the exact map produced by the n2 strategy for small sizes");
        Mockito.verify(parser, Mockito.times(1)).parseData(raw);
        Mockito.verify(n2, Mockito.times(1)).countNeighbors(dto);
        Mockito.verifyNoInteractions(plane);
    }
    
    @Test
    void appriseLocations_usesPlane_whenSizeGreaterThan10000() throws Exception {
        LocationsParserWithR parser = Mockito.mock(LocationsParserWithR.class);
        NeighborsCountingStrategy plane = Mockito.mock(NeighborsCountingStrategy.class);
        Map<String, NeighborsCountingStrategy> strategies = strategiesMap(
                Map.entry(PLANE_NAME, plane)
        );

        LocationsDTOWithR dto = mockDtoWithSize(10001);
        List<String> raw = someRawData();

        Mockito.when(parser.parseData(raw)).thenReturn(dto);

        Map<Long, Long> expected = Collections.singletonMap(13L, 99L);
        Mockito.when(plane.countNeighbors(dto)).thenReturn(expected);

        LocationsAppriserByNeighborsCount appriser = createAppriser(parser, strategies);

        Map<Long, Long> result = appriser.appriseLocations(raw);

        assertSame(expected, result, "Returned map should be the exact map produced by the plane strategy for large sizes");
        Mockito.verify(parser, Mockito.times(1)).parseData(raw);
        Mockito.verify(plane, Mockito.times(1)).countNeighbors(dto);
    }
    
    @Test
    void appriseLocations_throwsUnsupportedOperationException_whenStrategyMissing() throws Exception {
        LocationsParserWithR parser = Mockito.mock(LocationsParserWithR.class);
        NeighborsCountingStrategy n2 = Mockito.mock(NeighborsCountingStrategy.class);
        // intentionally do NOT provide the plane strategy so lookup will fail for large sizes
        Map<String, NeighborsCountingStrategy> strategies = strategiesMap(
                Map.entry(N2_NAME, n2)
        );

        LocationsDTOWithR dto = mockDtoWithSize(10050); // > 10000 -> will look for plane strategy
        List<String> raw = someRawData();

        Mockito.when(parser.parseData(raw)).thenReturn(dto);

        LocationsAppriserByNeighborsCount appriser = createAppriser(parser, strategies);

        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> appriser.appriseLocations(raw),
                "When a required strategy is missing, an UnsupportedOperationException must be thrown"
        );

        String expectedMessage = "Neighbors counting strategy with name " + PLANE_NAME + " does not exist.";
        assertEquals(expectedMessage, ex.getMessage(), "Exception message should describe the missing strategy by name");
        Mockito.verify(parser, Mockito.times(1)).parseData(raw);
        Mockito.verifyNoMoreInteractions(n2);
    }
    
    @Test
    void appriseLocations_propagatesIOException_fromParser() throws Exception {
        LocationsParserWithR parser = Mockito.mock(LocationsParserWithR.class);
        NeighborsCountingStrategy n2 = Mockito.mock(NeighborsCountingStrategy.class);
        Map<String, NeighborsCountingStrategy> strategies = strategiesMap(
                Map.entry(N2_NAME, n2)
        );

        List<String> raw = someRawData();
        IOException io = new IOException("parse failed");
        Mockito.when(parser.parseData(raw)).thenThrow(io);

        LocationsAppriserByNeighborsCount appriser = createAppriser(parser, strategies);

        IOException thrown = assertThrows(
                IOException.class,
                () -> appriser.appriseLocations(raw),
                "IOException from parser.parseData should be propagated"
        );

        assertSame(io, thrown, "The propagated IOException should be the same instance thrown by the parser");
        Mockito.verify(parser, Mockito.times(1)).parseData(raw);
        Mockito.verifyNoInteractions(n2);
    }
}


package com.naumen_contest.locationsfinder.Parser;

import com.naumen_contest.locationsfinder.Config;
import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Model.Location;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsParserWithRTest {
    @Mock
    private Config mockConfig;

    @InjectMocks
    private LocationsParserWithR parser;
    
    double delta = 1e-6;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockConfig.getColumnSeparator()).thenReturn(" ");
        when(mockConfig.getDecimalSeparator()).thenReturn(".");
        parser = new LocationsParserWithR(mockConfig);
    }
    
    @Test
    void testParseData_testDataO() throws IOException {
        int count = 2;
        double radius = 10.0;
        List<String> rawData = createTestData(count, radius);

        LocationsDTOWithR dto = parser.parseData(rawData);

        assertEquals(radius, dto.getRadius(), delta);
        assertEquals(count, dto.getSize());
        
        List<Location> locations = dto.getLocations();
        assertEquals(count, locations.size());
        assertEquals(0.0, locations.get(0).X(), delta);
        assertEquals(1.0, locations.get(0).Y(), delta);
        assertEquals(1.0, locations.get(1).X(), delta);
        assertEquals(2.0, locations.get(1).Y(), delta);
    }
    
    @Test
    void testParseData_singleLocation() throws IOException {
        int count = 1;
        double radius = 10.0;
        List<String> rawData = createTestData(count, radius);

        LocationsDTOWithR dto = parser.parseData(rawData);

        assertEquals(count, dto.getSize());
        assertEquals(radius, dto.getRadius(), delta);
        assertEquals(0.0, dto.getLocations().get(0).X(), delta);
        assertEquals(1.0, dto.getLocations().get(0).Y(), delta);
    }
    
    // Тестируем парсинг с нулевым количеством точек
    @Test
    void testParseData_zeroLocations() throws IOException {
        int count = 0;
        double radius = 20.0;
        List<String> rawData = createTestData(count, radius);

        LocationsDTOWithR dto = parser.parseData(rawData);

        assertEquals(count, dto.getSize());
        assertEquals(radius, dto.getRadius(), delta);
        assertTrue(dto.getLocations().isEmpty());
    }
    
    @Test
    void testParseData_emptyData() {
        List<String> rawData = new ArrayList<>();
        
        Exception exception = assertThrows(IOException.class, () -> {
            parser.parseData(rawData);
        });
    }
    
    @Test
    void testParseData_invalidHeaderFormat_throwsIOException() {
        List<String> rawData = new ArrayList<>();
        rawData.add("invalid_header");
        rawData.add("1.0 2.0");
        
        Exception exception = assertThrows(IOException.class, () -> {
            parser.parseData(rawData);
        });
    }
    
    @Test
    void testParseData_mismatchedSize_less() {
        List<String> rawData = new ArrayList<>();
        rawData.add("2 10.0");
        rawData.add("1.0 2.0");
        
        Exception exception = assertThrows(IOException.class, () -> {
            parser.parseData(rawData);
        });
    }
    
    void testParseData_mismatchedSize_more() {
        List<String> rawData = new ArrayList<>();
        rawData.add("2 10.0");
        rawData.add("1.0 2.0");
        rawData.add("1.0 2.0");
        rawData.add("1.0 2.0");
        
        Exception exception = assertThrows(IOException.class, () -> {
            parser.parseData(rawData);
        });
    }
    
    @Test
    void testParseData_invalidDataLine_throwsNumberFormatException() {
        List<String> rawData = new ArrayList<>();
        rawData.add("1 10.0");
        rawData.add("invalid_data");
        
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            parser.parseData(rawData);
        });
    }
    
    private List<String> createTestData(int count, double radius) {
        List<String> data = new ArrayList<>();
        data.add(count + " " + radius);
        for (int i = 0; i < count; i++) {
            data.add((double) i + " " + (double) (i + 1));
        }
        return data;
    }
}

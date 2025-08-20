
package com.naumen_contest.locationsfinder.Service;

import com.naumen_contest.locationsfinder.Dao.LocationsDAO;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsFinderByScoreTest {
    
    private LocationsDAO daoMockSupporting(String... formats) {
        LocationsDAO dao = Mockito.mock(LocationsDAO.class);
        Mockito.when(dao.getSupportedFormats()).thenReturn(Arrays.asList(formats));
        return dao;
    }
    
    private Map<String, LocationsAppriser> apprisersMap(Map.Entry<String, LocationsAppriser>... entries) {
        Map<String, LocationsAppriser> m = new HashMap<>();
        for (Map.Entry<String, LocationsAppriser> e : entries) {
            m.put(e.getKey(), e.getValue());
        }
        return m;
    }
    
    private List<LocationsDAO> daosList(LocationsDAO... daos) {
        return Arrays.asList(daos);
    }
    
    private List<String> sampleRawData() {
        return Arrays.asList("one", "two", "three");
    }
    
    @Test
    void findLocations_writesSortedLimitedResults_and_usesCorrectDaos() throws Exception {
        String inputFile = "in.txt";
        String outputFile = "out.log";
        String criteria = "Score";
        long limit = 3L;

        LocationsDAO daoTxt = daoMockSupporting("txt");
        LocationsDAO daoLog = daoMockSupporting("log");

        List<LocationsDAO> daos = daosList(daoTxt, daoLog);

        LocationsAppriser appriser = Mockito.mock(LocationsAppriser.class);
        Map<String, LocationsAppriser> apprisers = apprisersMap(
                Map.entry("locationsAppriser" + criteria, appriser)
        );

        LocationsFinderByScore finder = new LocationsFinderByScore(apprisers, daos);
        Mockito.clearInvocations(daoTxt, daoLog, appriser);

        LinkedHashMap<Long, Long> score = new LinkedHashMap<>();
        score.put(1L, 2L);
        score.put(2L, 5L);
        score.put(3L, 5L);
        score.put(4L, 1L);

        List<String> raw = sampleRawData();
        Mockito.when(daoTxt.readFromFile(inputFile)).thenReturn(raw);
        Mockito.when(appriser.appriseLocations(raw)).thenReturn(score);

        finder.findLocations(inputFile, outputFile, criteria, limit);

        List<String> expected = Arrays.asList("2 5", "3 5", "1 2");

        Mockito.verify(daoTxt, Mockito.times(1)).readFromFile(inputFile);
        Mockito.verify(appriser, Mockito.times(1)).appriseLocations(raw);
        Mockito.verify(daoLog, Mockito.times(1)).writeToFile(outputFile, expected);

        Mockito.verifyNoMoreInteractions(daoTxt, daoLog, appriser);
    }
    
    @Test
    void findLocations_limitZero_writesEmptyOutputList() throws Exception {
        String inputFile = "data.csv";
        String outputFile = "result.out";
        String criteria = "Score";
        long limit = 0L;

        LocationsDAO daoCsv = daoMockSupporting("csv");
        LocationsDAO daoOut = daoMockSupporting("out");
        List<LocationsDAO> daos = daosList(daoCsv, daoOut);

        LocationsAppriser appriser = Mockito.mock(LocationsAppriser.class);
        Map<String, LocationsAppriser> apprisers = apprisersMap(
                Map.entry("locationsAppriser" + criteria, appriser)
        );

        LocationsFinderByScore finder = new LocationsFinderByScore(apprisers, daos);
        Mockito.clearInvocations(daoCsv, daoOut, appriser);

        List<String> raw = sampleRawData();
        LinkedHashMap<Long, Long> score = new LinkedHashMap<>();
        score.put(10L, 100L);
        score.put(11L, 90L);

        Mockito.when(daoCsv.readFromFile(inputFile)).thenReturn(raw);
        Mockito.when(appriser.appriseLocations(raw)).thenReturn(score);

        finder.findLocations(inputFile, outputFile, criteria, limit);

        List<String> expectedEmpty = Collections.emptyList();

        Mockito.verify(daoCsv, Mockito.times(1)).readFromFile(inputFile);
        Mockito.verify(appriser, Mockito.times(1)).appriseLocations(raw);
        Mockito.verify(daoOut, Mockito.times(1)).writeToFile(outputFile, expectedEmpty);
        Mockito.verifyNoMoreInteractions(daoCsv, daoOut, appriser);
    }
    
    @Test
    void findLocations_throwsWhenInputFileExtensionNotSupported() {
        LocationsDAO daoTxt = daoMockSupporting("txt");
        List<LocationsDAO> daos = daosList(daoTxt);

        Map<String, LocationsAppriser> apprisers = Collections.emptyMap();
        LocationsFinderByScore finder = new LocationsFinderByScore(apprisers, daos);

        String inputFile = "something.unknown";
        String outputFile = "out.txt";

        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> finder.findLocations(inputFile, outputFile, "X", 10L),
                "When input file extension is not supported, UnsupportedOperationException should be thrown"
        );

        String expectedExt = "unknown";
        String expectedMessage = "Input data file with '%s' extenshion does not supported.".formatted(expectedExt);
        assertEquals(expectedMessage, ex.getMessage(), "Exception message should mention unsupported extension exactly");
    }
    
    @Test
    void findLocations_throwsWhenAppriserMissing_and_messageContainsSupportedKeys() throws Exception {
        LocationsDAO daoTxt = daoMockSupporting("txt");
        List<LocationsDAO> daos = daosList(daoTxt);

        LocationsAppriser present = Mockito.mock(LocationsAppriser.class);
        Map<String, LocationsAppriser> apprisers = apprisersMap(
                Map.entry("locationsAppriserFoo", present)
        );

        LocationsFinderByScore finder = new LocationsFinderByScore(apprisers, daos);

        String inputFile = "in.txt";
        String outputFile = "out.txt";
        String requestedCriteria = "Bar"; // will look for "locationsAppriserBar"
        List<String> raw = sampleRawData();

        Mockito.when(daoTxt.readFromFile(inputFile)).thenReturn(raw);

        UnsupportedOperationException ex = assertThrows(
                UnsupportedOperationException.class,
                () -> finder.findLocations(inputFile, outputFile, requestedCriteria, 5L),
                "Missing appriser should cause UnsupportedOperationException"
        );

        String expectedPrefix = "Not supported apprising criteria: locationsAppriser" + requestedCriteria + ". Now are supported:\n";
        assertTrue(ex.getMessage().startsWith(expectedPrefix), "Exception message should start with the explanatory prefix");
        // message should list available keys (here 'locationsAppriserFoo')
        assertTrue(ex.getMessage().contains("locationsAppriserFoo"), "Exception message should list available appriser keys");
        Mockito.verify(daoTxt, Mockito.times(1)).readFromFile(inputFile);
        Mockito.verifyNoMoreInteractions(present);
    }
}

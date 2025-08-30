package com.naumen_contest.locationsfinder;

import com.naumen_contest.locationsfinder.Model.Location;
import com.naumen_contest.locationsfinder.Service.LocationsFinderByScore;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest()
@ComponentScan(basePackages = "com.naumen_contest.locationsfinder")
//@ActiveProfiles("test")
class LocationsFinderByScoreIntegrationTest {
    
    private String inputFile = "input.txt";
    private String outputFile = "output.txt";
    private String criteria = "ByNeighborsCount";
    
    
    @Autowired
    LocationsFinderByScore lf;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setBaseId() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field countField = Location.class.getDeclaredField("count");
        countField.setAccessible(true);
        countField.setInt(Location.class, 0);
    }
        
    private void _createInputFile(String fileName, List<String> data) throws IOException {
        Path filePath = tempDir.resolve(fileName);
        Files.write(filePath, data, StandardCharsets.UTF_8);
    }
    
    private List<String> _readOutputData(String fileName) throws IOException {
        Path filePath = tempDir.resolve(fileName);
        return Files.readAllLines(filePath, StandardCharsets.UTF_8);
    }

    @Test
    void sampleData1Test() throws IOException {
        List<String> inputData = List.of(
                "5 3",
                "0 0",
                "2 -2",
                "5 3",
                "-2 2",
                "5 1"
        );
        List<String> outputData = List.of(
                "0 2",
                "1 1",
                "2 1",
                "3 1",
                "4 1"
        );
        _createInputFile(inputFile, inputData);
        Path outputPath = tempDir.resolve(outputFile);
        Path inputPath = tempDir.resolve(inputFile);
        System.out.println("PTH:" + outputPath.toString());
        lf.findLocations(inputPath.toString(), outputPath.toString(), criteria, 10);
        
        
        assertFalse(Files.notExists(outputPath), "Output file does not exist.");
        List<String> result = Files.readAllLines(outputPath);
        assertEquals(outputData.size(), result.size(), "Size of output file does not correspond to expected lines count.");
        for (int i = 0; i < outputData.size(); i++) {
            assertTrue(outputData.get(i).equals(result.get(i)), "Actual line {%s} does not correspond to expected {%s}.".formatted(result.get(i), outputData.get(i)));
        }
    }

    @Test
    void sampleData2Test() throws IOException {
        List<String> inputData = List.of(
                "10 3.000000",
                "3.168070 1.752490",
                "0.500730 6.436580",
                "0.089300 0.112720",
                "2.275440 7.508780",
                "0.779230 4.377090",
                "0.644400 1.381650",
                "1.844920 1.430420",
                "8.079870 5.225030",
                "7.823270 5.317290",
                "1.788400 5.426120"
        );
        List<String> outputData = List.of(
                "5 4",
                "1 3",
                "4 3",
                "6 3",
                "9 3",
                "0 2",
                "2 2",
                "3 2",
                "7 1",
                "8 1"
        );
        _createInputFile(inputFile, inputData);
        Path outputPath = tempDir.resolve(outputFile);
        Path inputPath = tempDir.resolve(inputFile);
        System.out.println("PTH:" + outputPath.toString());
        lf.findLocations(inputPath.toString(), outputPath.toString(), criteria, 10);
        
        
        assertFalse(Files.notExists(outputPath), "Output file does not exist.");
        List<String> result = Files.readAllLines(outputPath);
        assertEquals(outputData.size(), result.size(), "Size of output file does not correspond to expected lines count.");
        for (int i = 0; i < outputData.size(); i++) {
            assertTrue(outputData.get(i).equals(result.get(i)), "Actual line {%s} does not correspond to expected {%s}.".formatted(result.get(i), outputData.get(i)));
        }
    }
    
    @Test
    void sampleData2PlaneGridTest() throws IOException {
        int repeatCount = 1100;
        int limit = 11000;
        List<String> pointsList = List.of(
                "10 3.000000",
                "3.168070 1.752490",
                "0.500730 6.436580",
                "0.089300 0.112720",
                "2.275440 7.508780",
                "0.779230 4.377090",
                "0.644400 1.381650",
                "1.844920 1.430420",
                "8.079870 5.225030",
                "7.823270 5.317290",
                "1.788400 5.426120"
        );
        List<Integer> resList = List.of(
                4,
                3,
                3,
                3,
                3,
                2,
                2,
                2,
                1,
                1
        );
        List<Integer> idList = List.of(
                5,
                1,
                4,
                6,
                9,
                0,
                2,
                3,
                7,
                8
        );
        List<String> inputData = new ArrayList<>();
        List<String> outputData = new ArrayList<>();
        inputData.add(repeatCount*(pointsList.size() - 1) + " 3.000000");
        for (int j = 1; j < pointsList.size(); j++) {
            for (int i = 0; i < repeatCount; i++) {
                inputData.add(pointsList.get(j));
            }
        }
        for (int j = 0; j < idList.size(); j++) {
        int score = resList.get(j)*repeatCount + repeatCount - 1;
            for (int i = 0; i < repeatCount; i++) {
                int id = idList.get(j)*repeatCount + i;
                outputData.add(id + " " + score);
            }
        }
        _createInputFile(inputFile, inputData);
        Path outputPath = tempDir.resolve(outputFile);
        Path inputPath = tempDir.resolve(inputFile);
        System.out.println("PTH:" + outputPath.toString());
        lf.findLocations(inputPath.toString(), outputPath.toString(), criteria, limit);
        
        
        assertFalse(Files.notExists(outputPath), "Output file does not exist.");
        List<String> result = Files.readAllLines(outputPath);
        int maxIdx = Math.min(outputData.size(), limit);
        assertEquals(maxIdx, result.size(), "Size of output file does not correspond to expected lines count.");
        for (int i = 0; i < maxIdx; i++) {
            assertTrue(outputData.get(i).equals(result.get(i)), "Actual line {%s} does not correspond to expected {%s}.".formatted(result.get(i), outputData.get(i)));
        }
    }
}

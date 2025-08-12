
package com.naumen_contest.locationsfinder.Dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsDaoTxtTest {

    LocationsDAOTxt dao;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() {
        dao = new LocationsDAOTxt();
    }
    
    @Test
    void readFromFile_testData() throws IOException {
        List<String> testData = List.of("line1", "line2", "line3");
        String fileName = "testFile.txt";
        createTestFile(fileName, testData);
        
        List<String> readLines = dao.readFromFile(tempDir.resolve(fileName).toString());
        
        assertEquals(testData, readLines);
    }
    
    @Test
    void readFromFile_fileNotExist() {
        String nonExistentFileName = "nonExistent.txt";
        
        assertThrows(IOException.class, () -> {
            dao.readFromFile(tempDir.resolve(nonExistentFileName).toString());
        });
    }
    
    @Test
    void writeToFile_newFile() throws IOException {
        List<String> testData = List.of("line1", "line2", "line3");
        String fileName = "testFile.txt";
        
        dao.writeToFile(tempDir.resolve(fileName).toString(), testData);
        
        Path filePath = tempDir.resolve(fileName);
        assertTrue(Files.exists(filePath), "Write data method does not create correct file.");
        List<String> readLines = Files.readAllLines(filePath);
        
        assertEquals(testData, readLines);
    }
    
    @Test
    void testWriteToFile_existentFile() throws IOException {
        List<String> initialData = List.of("old data");
        String fileName = "testFile.txt";
        createTestFile(fileName, initialData);
        
        List<String> newData = List.of("new data");
        dao.writeToFile(tempDir.resolve(fileName).toString(), newData);
        
        Path filePath = tempDir.resolve(fileName);
        List<String> fileData = Files.readAllLines(filePath);
        assertEquals(newData, fileData);
    }
    
    private Path createTestFile(String fileName, List<String> content) throws IOException {
        Path filePath = tempDir.resolve(fileName);
        Files.write(filePath, content);
        return filePath;
    }
    
    @Test
    void testGetSupportedFormats_returnsCorrectList() {
        List<String> supportedFormats = dao.getSupportedFormats();

        assertTrue(supportedFormats.contains("txt"), "Список должен содержать 'txt'");
        assertTrue(supportedFormats.contains("rtf"), "Список должен содержать 'rtf'");
    }
    
    private void createRandomInputFile(
            Path path, 
            int intMin, 
            int intMax, 
            double dblMin, 
            double dblMax,
            String doubleFormat
    ) throws IOException {
        int N = ThreadLocalRandom.current().nextInt(intMin, intMax);
        double range = ThreadLocalRandom.current().nextDouble(dblMin, dblMax);
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bw.write(("%i " + doubleFormat + "\n").formatted(N, range));
            for(int i = 0; i < N; i++) {
                double x = ThreadLocalRandom.current().nextDouble(dblMin, dblMax);
                double y = ThreadLocalRandom.current().nextDouble(dblMin, dblMax);
                bw.write((doubleFormat + " " + doubleFormat + "\n").formatted(x, y));
            }
        }
    }
}

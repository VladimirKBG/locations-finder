
package com.naumen_contest.locationsfinder.Dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsDaoTxtTest {

    @TempDir
    Path tempDir;
    
    
    
    
    private void createInputFile(Path path, int N, double range, List<String> locs) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bw.write("%i %d\n".formatted(N, range));
            for(String line : locs) {
                bw.write(line + "\n");
            }
        }
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

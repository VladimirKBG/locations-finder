package com.naumen_contest.locationsfinder.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class RandomInputDataGenerator {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("C:\\Users\\user\\Desktop\\PRJ\\NeighborPointsWithDistanceLessThanCounter\\locationsfinder\\input_10_000_000.txt");
        createRndLocations_XY(path, 10_000_000);
    }
    
    public static void createRndLocations_XY(Path path, long count) throws IOException {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        long range = 200;
        long limit = 100_000;
        
        Files.writeString(path, count + " " + range + "\n");
        List<String> lines = new ArrayList<String>((int)count);
        for (long i = 0; i < count; i++) {
            //Files.writeString(path, rng.nextLong(-limit, limit) + " " + rng.nextLong(-limit, limit) + "\n", StandardOpenOption.APPEND);
            lines.add(rng.nextLong(-limit, limit) + " " + rng.nextLong(-limit, limit) + "\n");
        }
        Files.write(path, lines, StandardOpenOption.APPEND);
    }
}

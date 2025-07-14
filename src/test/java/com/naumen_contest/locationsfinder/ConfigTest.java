package com.naumen_contest.locationsfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */

@SpringBootTest
@TestPropertySource(properties = {
    "bruteForceThreshold = 10",
    "outputLocationsCount = 10",
    "locationIdBase = 0",
    "inputDataPath = input.txt",
    "outputDataPath = output.txt",
    "calculationAccuracy = 6",
})
public class ConfigTest {
    @Autowired
    Config config;
    
    @Test
    void fieldsLoadsFromProperties() {
        System.out.println("fieldsLoadsFromProperties test started");
        assertEquals(config.getBruteForceThreshold(), 10);
        assertEquals(config.getOutputLocationsCount(), 10);
        assertEquals(config.getLocationIdBase(), 0);
        assertEquals(config.getInputDataPath(), "input.txt");
        assertEquals(config.getOutputDataPath(), "output.txt");
        assertEquals(config.getCalculationAccuracy(), 6);
    }

}

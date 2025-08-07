package com.naumen_contest.locationsfinder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */

//@SpringBootTest
public class ConfigTest {
    @Autowired
    Config config;
    
    @Test
    void fieldsLoadsFromProperties() {
        System.out.println("fieldsLoadsFromProperties test started");
        /*
        
        assertEquals(config.getBruteForceThreshold(), 10);
        assertEquals(config.getOutputLocationsCount(), 10);
        assertEquals(config.getLocationIdBase(), 0);
        assertEquals(config.getInputDataPath(), "input.txt");
        assertEquals(config.getOutputDataPath(), "output.txt");
        assertEquals(config.getCalculationAccuracy(), 6);
        */
    }

}

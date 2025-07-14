
package com.naumen_contest.locationsfinder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
@ConfigurationProperties()
public class Config {
    
    private static final Config instance = new Config();
    
    private final int bruteForceThreshold = 10;
    private final int outputLocationsCount = 10;
    private final int locationIdBase = 0;
    private final String inputDataPath = "input.txt";
    private final String outputDataPath = "output.txt";
    private final int calculationAccuracy = 6;
    
    private Config() {
    }

    public static Config getInstance() {
        return instance;
    }

    public int getBruteForceThreshold() {
        return bruteForceThreshold;
    }

    public int getOutputLocationsCount() {
        return outputLocationsCount;
    }

    public int getLocationIdBase() {
        return locationIdBase;
    }

    public String getInputDataPath() {
        return inputDataPath;
    }

    public String getOutputDataPath() {
        return outputDataPath;
    }

    public int getCalculationAccuracy() {
        return calculationAccuracy;
    }
    
    @Override
    public String toString() {
        return "Config{" + "bruteForceThreshold=" + bruteForceThreshold + ", outputLocationsCount=" + outputLocationsCount + ", locationIdBase=" + locationIdBase + ", inputDataPath=" + inputDataPath + ", outputDataPath=" + outputDataPath + ", calculationAccuracy=" + calculationAccuracy + '}';
    }
    
}

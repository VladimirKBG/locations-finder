
package com.naumen_contest.locationsfinder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
@ConfigurationProperties
public class Config {
    
    private static final Config instance = new Config();
    
    private final String inputDataPath = "input.txt";
    private final String outputDataPath = "output.txt";
    private final String applicationRunMode = "byScore";
    private final String locationsScoreCriteria = "neighborsCount";
    private final long outputLocationsCount = 10;
    private final String columnSeparator = "\\s";
    private final String decimalSeparator = "\\.";
    
    private final int bruteForceThreshold = 10;
    private final int locationIdBase = 0;

    private final int calculationAccuracy = 6;
    
    private Config() {
    }

    public static Config getInstance() {
        return instance;
    }

    public int getBruteForceThreshold() {
        return bruteForceThreshold;
    }

    public long getOutputLocationsCount() {
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

    public String getApplicationRunMode() {
        return applicationRunMode;
    }

    public String getLocationsScoreCriteria() {
        return locationsScoreCriteria;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }
    
    
    
    
    @Override
    public String toString() {
        return "Config{" + "bruteForceThreshold=" + bruteForceThreshold + ", outputLocationsCount=" + outputLocationsCount + ", locationIdBase=" + locationIdBase + ", inputDataPath=" + inputDataPath + ", outputDataPath=" + outputDataPath + ", calculationAccuracy=" + calculationAccuracy + '}';
    }
    
}

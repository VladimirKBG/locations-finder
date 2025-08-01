package com.naumen_contest.locationsfinder;

import com.naumen_contest.locationsfinder.Service.LocationsFinder;
import java.io.IOException;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
public class AppRunner implements CommandLineRunner {
    private final Map<String, LocationsFinder> lfs;
    private final Config cfg;

    public AppRunner(Map<String, LocationsFinder> lfs, Config cfg) {
        this.lfs = lfs;
        this.cfg = cfg;
    }
    
    @Override
    public void run(String... args) throws IOException {
        String inputFile = cfg.getInputDataPath();
        String outputFile = cfg.getOutputDataPath();
        String mode = cfg.getApplicationRunMode();
        String criteria = cfg.getLocationsScoreCriteria();
        long limit = cfg.getOutputLocationsCount();
        
        if (!lfs.containsKey(mode)) {
            throw new UnsupportedOperationException("Not supported application mode: " + mode + ".");
        }
        
        lfs.get(mode).findLocations(inputFile, outputFile, criteria, limit);
    }

}

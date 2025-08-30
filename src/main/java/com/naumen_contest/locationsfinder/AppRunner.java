package com.naumen_contest.locationsfinder;

import com.naumen_contest.locationsfinder.Service.LocationsFinder;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger log = LoggerFactory.getLogger(AppRunner.class);

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
        
        _getLocationFinder(mode).findLocations(inputFile, outputFile, criteria, limit);
    }
    
    private LocationsFinder _getLocationFinder(String mode) {
        String key = "locationsFinder" + mode;
        if (!lfs.containsKey(key)) {
            StringBuilder msg = new StringBuilder();
            msg.append("Not supported application mode: " + key + ". Now are supported:\n");
            for (Map.Entry<String, LocationsFinder> entry : this.lfs.entrySet()) {
                msg.append(entry.getKey() + "\n");
            }
            throw new UnsupportedOperationException(msg.toString());
        }
        LocationsFinder lf = lfs.get(key);
        log.info("In AppRunner {} are choosen.", lf.toString());
        return lf;
    }
}

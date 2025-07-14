package com.naumen_contest.locationsfinder;

import com.naumen_contest.locationsfinder.Service.LocationsFinderService;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
public class AppRunner implements CommandLineRunner {
    private final LocationsFinderService lfs;

    public AppRunner(LocationsFinderService lfs) {
        this.lfs = lfs;
    }
    
    @Override
    public void run(String... args) throws IOException {
        lfs.ProcessLocationsFromFile("input.txt", "output.txt");
    }

}

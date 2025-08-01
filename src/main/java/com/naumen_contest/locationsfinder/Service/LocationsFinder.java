package com.naumen_contest.locationsfinder.Service;

import java.io.IOException;

/**
 *
 * @author Vladimir Aleksentsev
 */
public interface LocationsFinder {
    public void findLocations(
            String inputFile, 
            String outputFile, 
            String criteria, 
            long limit
    ) throws IOException;
    
}

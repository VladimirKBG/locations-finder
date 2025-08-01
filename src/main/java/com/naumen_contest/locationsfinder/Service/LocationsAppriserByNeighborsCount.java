
package com.naumen_contest.locationsfinder.Service;

import com.naumen_contest.locationsfinder.Service.Parser.LocationsParserWithR;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsAppriserByNeighborsCount implements LocationsAppriser {
    private final LocationsParserWithR parser;

    public LocationsAppriserByNeighborsCount(LocationsParserWithR parser) {
        this.parser = parser;
    }
    
    

    @Override
    public Map<Long, Long> appriseLocations(List<String> rawData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

}

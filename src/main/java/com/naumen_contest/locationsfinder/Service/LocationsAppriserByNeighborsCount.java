
package com.naumen_contest.locationsfinder.Service;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Service.Parser.LocationsParserWithR;
import com.naumen_contest.locationsfinder.Service.Strategy.NeighborsCountingStrategy;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
public class LocationsAppriserByNeighborsCount implements LocationsAppriser {
    private final LocationsParserWithR parser;
    private final Map<String, NeighborsCountingStrategy> strategies;

    public LocationsAppriserByNeighborsCount(LocationsParserWithR parser, Map<String, NeighborsCountingStrategy> strategies) {
        this.parser = parser;
        this.strategies = strategies;
    }

    @Override
    public Map<Long, Long> appriseLocations(List<String> rawData) throws IOException {
        LocationsDTOWithR dto = parser.parseData(rawData);
        NeighborsCountingStrategy strategy;
        if (dto.getSize() > 10000) {
            strategy = _getStrategy("planeGridNeighborsCountingStrategy");
        } else {
            strategy = _getStrategy("n2NeighborsCountingStrategy");
        }
        return strategy.countNeighbors(dto);
    }
    
    private NeighborsCountingStrategy _getStrategy(String strategyName) {
        if (!strategies.containsKey(strategyName)) {
            throw new UnsupportedOperationException(
                    "Neighbors counting strategy with name " + strategyName + " does not exist."
            );
        }
        return strategies.get(strategyName);  
    }
    

}

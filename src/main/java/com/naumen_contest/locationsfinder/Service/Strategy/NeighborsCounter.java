
package com.naumen_contest.locationsfinder.Service.Strategy;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Service.Dto.OutputLocationsDto;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class NeighborsCounter {
    public OutputLocationsDto getNeighborsCount(LocationsDTOWithR dto) {
        NeighborsCountingStrategy strategy;
        if (dto.getSize() < 10) {
            strategy = new NaiveNeighborsCountingStrategy();
        } else {
            strategy = new KDTreeNeighborsCountingStrategy();
        }
        return strategy.countNeighbors(dto);
    }

}

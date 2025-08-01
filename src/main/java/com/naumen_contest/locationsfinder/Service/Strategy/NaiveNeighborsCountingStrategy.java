
package com.naumen_contest.locationsfinder.Service.Strategy;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Service.Dto.OutputLocationsDto;
import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class NaiveNeighborsCountingStrategy implements NeighborsCountingStrategy {
    


    @Override
    public OutputLocationsDto countNeighbors(LocationsDTOWithR inputDto) {
        List<Location> locs = inputDto.getLocations();
        double R = Math.pow(inputDto.getRadius(), 2);
        Map<Long, Long> count = new HashMap<>(locs.size());
        for (int i = 0; i < locs.size() - 1; i++) {
            for (int j = i + 1; j < locs.size(); j++) {
                if (locs.get(i).squareDistanceTo(locs.get(j)) <= R) {
                    
                }
            }
        }
        return null;
    }
    

}

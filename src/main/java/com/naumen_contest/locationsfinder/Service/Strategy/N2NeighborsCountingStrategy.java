
package com.naumen_contest.locationsfinder.Service.Strategy;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
public class N2NeighborsCountingStrategy implements NeighborsCountingStrategy {

    @Override
    public Map<Long, Long> countNeighbors(LocationsDTOWithR inputDto) {
        List<Location> locs = inputDto.getLocations();
        double R = Math.pow(inputDto.getRadius(), 2);
        Map<Long, Long> score = new HashMap<>(locs.size());
        for (Location loc : locs) {
            score.put(loc.Id(), 0L);
        }
        Location loc1;
        Location loc2;
        long val;
        for (int i = 0; i < locs.size() - 1; i++) {
            loc1 = locs.get(i);
            for (int j = i + 1; j < locs.size(); j++) {
                loc2 = locs.get(j);
                if (loc1.squareDistanceTo(locs.get(j)) <= R) {
                    _incrementScore(score, loc1.Id());
                    _incrementScore(score, loc2.Id());
                }
            }
        }
        return score;
    }
}

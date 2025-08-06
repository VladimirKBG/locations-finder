
package com.naumen_contest.locationsfinder.Service.Strategy;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Service.Model.Location;
import com.naumen_contest.locationsfinder.Service.Model.PlaneGrid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
public class PlaneGridNeighborsCountingStrategy implements NeighborsCountingStrategy {

    @Override
    public Map<Long, Long> countNeighbors(LocationsDTOWithR inputDto) {
        List<Location> locs = inputDto.getLocations();
        double R = inputDto.getRadius();
        double R2 = R*R;
        Map<Long, Long> score = new HashMap<>(locs.size());
       
        for (Location loc : locs) {
            score.put(loc.Id(), 0L);
        }
        PlaneGrid pg = new PlaneGrid(inputDto);
        for (Location loc : locs) {
            for (var locList : pg.getNearestCells(loc.X(), loc.Y())) {
                for (Location anotherLoc : locList) {
                    if (loc.squareDistanceTo(anotherLoc) <= R2) {
                        _incrementScore(score, loc.Id());
                        _incrementScore(score, anotherLoc.Id());
                    }
                }
            }
            pg.addEntity(loc, loc.X(), loc.Y());
        }
        return score;
    }
}

package com.naumen_contest.locationsfinder.Service.Strategy;

import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import java.util.Map;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public interface NeighborsCountingStrategy {
    
    public Map<Long, Long> countNeighbors(LocationsDTOWithR inputDto);
    
    default void _incrementScore(Map<Long, Long> map, long id) {
        long oldValue = map.get(id);
        map.put(id, oldValue + 1);
    }

}

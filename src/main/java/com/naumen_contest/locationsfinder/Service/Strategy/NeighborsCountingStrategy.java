
package com.naumen_contest.locationsfinder.Service.Strategy;

import com.naumen_contest.locationsfinder.Service.Dto.InputLocationsDto;
import com.naumen_contest.locationsfinder.Service.Dto.OutputLocationsDto;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public interface NeighborsCountingStrategy {
    
    public OutputLocationsDto countNeighbors(InputLocationsDto inputDto);

}

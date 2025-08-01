
package com.naumen_contest.locationsfinder.Service.Dto;

import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class OutputLocationsDto implements LocationsDTO1 {
    private final List<Location> locations;
    private final Map<Long, Long> score;

    public OutputLocationsDto(List<Location> locations, Map score) {
        this.locations = locations;
        this.score = score;
    } 
}

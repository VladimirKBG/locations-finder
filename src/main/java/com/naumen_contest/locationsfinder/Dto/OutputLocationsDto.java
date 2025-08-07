
package com.naumen_contest.locationsfinder.Dto;

import com.naumen_contest.locationsfinder.Model.Location;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class OutputLocationsDto implements LocationsDTO {
    private final List<Location> locations;
    private final Map<Long, Long> score;

    public OutputLocationsDto(List<Location> locations, Map score) {
        this.locations = locations;
        this.score = score;
    } 
}

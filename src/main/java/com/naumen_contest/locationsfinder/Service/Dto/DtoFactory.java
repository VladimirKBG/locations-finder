
package com.naumen_contest.locationsfinder.Service.Dto;

import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Component
public class DtoFactory {
    public InputLocationsDto getLocationsDto(int N, double radius, List<Location> locations) {
        return new InputLocationsDto(N, radius, locations);
    }
}

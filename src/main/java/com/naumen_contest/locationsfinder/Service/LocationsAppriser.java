package com.naumen_contest.locationsfinder.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Vladimir Aleksentsev
 */
public interface LocationsAppriser {
    public Map<Long, Long> appriseLocations(List<String> rawData);
    
}

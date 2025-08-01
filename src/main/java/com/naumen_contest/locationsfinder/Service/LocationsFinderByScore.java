
package com.naumen_contest.locationsfinder.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import com.naumen_contest.locationsfinder.Service.Dao.LocationsDAO1;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Service("byScore")
public class LocationsFinderByScore implements LocationsFinder{
    private final Map<String, LocationsAppriser> apprisers;
    private final Map<String, LocationsDAO1> daos;

    public LocationsFinderByScore(Map<String, LocationsAppriser> apprisers, Map<String, LocationsDAO1> daos) {
        this.apprisers = apprisers;
        this.daos = daos;
    }

    @Override
    public void findLocations(
            String inputFile, 
            String outputFile, 
            String criteria, 
            long limit) throws IOException {
        LocationsDAO1 inputDAO = _getDAO(inputFile);
        List<String> rawData = inputDAO.readFromFile(inputFile);
        
        if (!apprisers.containsKey(criteria)) {
            throw new UnsupportedOperationException("Input data file with '%s' extenshion does not supported.".formatted(criteria));
        }
        LocationsAppriser appriser = apprisers.get(criteria);
        Map<Long, Long> score =  appriser.appriseLocations(rawData);
        
        List<Map.Entry<Long, Long>> result = score.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    LocationsDAO1 _getDAO(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        if (!daos.containsKey(ext)) {
            throw new UnsupportedOperationException("Input data file with '%s' extenshion does not supported.".formatted(ext));
        }
        return daos.get(ext);
    }

}

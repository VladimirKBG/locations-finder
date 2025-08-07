
package com.naumen_contest.locationsfinder.Service;

import com.naumen_contest.locationsfinder.Dao.LocationsDAO;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Service()
public class LocationsFinderByScore implements LocationsFinder{
    private final Map<String, LocationsAppriser> apprisers;
    private final Map<String, LocationsDAO> daos;

    public LocationsFinderByScore(Map<String, LocationsAppriser> apprisers, List<LocationsDAO> daos) {
        this.apprisers = apprisers;
        this.daos = new HashMap<>();
        for (LocationsDAO dao : daos){
            for (String format : dao.getSupportedFormats()) {
                this.daos.put(format, dao);
            }
        }
    }

    @Override
    public void findLocations(
            String inputFile, 
            String outputFile, 
            String criteria, 
            long limit) throws IOException {
        LocationsDAO inputDAO = _getDAO(inputFile);
        List<String> rawData = inputDAO.readFromFile(inputFile);
        
        Map<Long, Long> score =  _getLocationsAppriser(criteria).appriseLocations(rawData);
        List<String> result = score.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(e -> e.getKey() + " " + e.getValue())
                .collect(Collectors.toList());
        
        LocationsDAO outputDAO = _getDAO(outputFile);
        outputDAO.writeToFile(outputFile, result);
    }
    
    private LocationsDAO _getDAO(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        if (!daos.containsKey(ext)) {
            throw new UnsupportedOperationException("Input data file with '%s' extenshion does not supported.".formatted(ext));
        }
        return daos.get(ext);
    }
    
    private LocationsAppriser _getLocationsAppriser(String criteria) {
        String key = "locationsAppriser" + criteria;
        if (!apprisers.containsKey(key)) {
            StringBuilder msg = new StringBuilder();
            msg.append("Not supported apprising criteria: " + key + ". Now are supported:\n");
            for (Map.Entry<String, LocationsAppriser> entry : this.apprisers.entrySet()) {
                msg.append(entry.getKey() + "\n");
            }
            throw new UnsupportedOperationException(msg.toString());
        }
        return apprisers.get(key);
    }
}

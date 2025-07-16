
package com.naumen_contest.locationsfinder.Service;

import com.naumen_contest.locationsfinder.Config;
import com.naumen_contest.locationsfinder.Service.Dao.DaoFactory;
import com.naumen_contest.locationsfinder.Service.Dao.LocationsDao;
import com.naumen_contest.locationsfinder.Service.Dto.DtoFactory;
import com.naumen_contest.locationsfinder.Service.Dto.LocationsDto;
import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
@Service
public class LocationsFinderService {
    private final Config config;
    private final DaoFactory daoFactory;
    private final DtoFactory dtoFactory;

    public LocationsFinderService(Config config, DaoFactory daoFactory, DtoFactory dtoFactory) {
        this.config = config;
        this.daoFactory = daoFactory;
        this.dtoFactory = dtoFactory;
    }
    
    public void ProcessLocationsFromFile(String inputFile, String outputFile) throws IOException {
        LocationsDao locationsDao = 
                daoFactory.getLocationsDao(inputFile); 
        
        LocationsDto inputData = locationsDao.readInputData(dtoFactory);
        
        
        
        
        
        System.out.println(inputData);
    }

}

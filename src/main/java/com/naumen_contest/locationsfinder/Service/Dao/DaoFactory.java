package com.naumen_contest.locationsfinder.Service.Dao;

import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vladimir Aleksentsev
 */
@Service
public class DaoFactory {
    
    public LocationsDAO1 getLocationsDao(String path) throws IOException {
        LocationsDAO1 reader;
        String ext = FilenameUtils.getExtension(path).toLowerCase();
        if (ext.equals("txt"))
            reader = new LocationsDAOTxt1(path);
        else
            throw new UnsupportedOperationException("Input data file with '%s' extenshion does not supported.".formatted(ext));
        return reader;
    }
    
}

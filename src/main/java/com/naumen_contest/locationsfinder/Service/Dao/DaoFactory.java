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
    
    public LocationsDAO222 getLocationsDao(String path) throws IOException {
        LocationsDAO222 reader;
        String ext = FilenameUtils.getExtension(path).toLowerCase();
        if (ext.equals("txt"))
            reader = new LocationsDAOTxt222(path);
        else
            throw new UnsupportedOperationException("Input data file with '%s' extenshion does not supported.".formatted(ext));
        return reader;
    }
    
}

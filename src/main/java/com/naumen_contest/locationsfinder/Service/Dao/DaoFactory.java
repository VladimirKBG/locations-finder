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
    
    public LocationsDAO getLocationsDao(String path) throws IOException {
        LocationsDAO reader;
        String ext = FilenameUtils.getExtension(path).toLowerCase();
        if (ext.equals("txt"))
            reader = new LocationsDaoTxt(path);
        else
            throw new UnsupportedOperationException("Input data file with '%s' extenshion does not supported.".formatted(ext));
        return reader;
    }
    
}

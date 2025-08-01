package com.naumen_contest.locationsfinder.Service.Dao;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Vladimir Aleksentsev
 */



public interface LocationsDAO {
    public List<String> readFromFile(String fileName) throws IOException;
}

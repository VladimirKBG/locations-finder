package com.naumen_contest.locationsfinder.Service.Dao;

import com.naumen_contest.locationsfinder.Service.Dto.DtoFactory;
import com.naumen_contest.locationsfinder.Service.Dto.InputLocationsDto;
import java.io.IOException;

/**
 *
 * @author Vladimir Aleksentsev
 */
public interface LocationsDao {
    public InputLocationsDto readInputData(DtoFactory containerFactory) throws IOException;
}

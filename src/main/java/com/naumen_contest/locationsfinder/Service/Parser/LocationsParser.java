package com.naumen_contest.locationsfinder.Service.Parser;

import com.naumen_contest.locationsfinder.Config;
import com.naumen_contest.locationsfinder.Service.Dto.LocationsDto;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Vladimir Aleksentsev
 */
public abstract class LocationsParser {
    protected final String separator;
    protected final String decimalSeparator;
    protected final String posInt;
    protected final String posDouble;
    protected final String dbl;
    
    private final Config cfg;
    
    public LocationsParser(Config cfg) {
        this.cfg = cfg;
        separator = cfg.getColumnSeparator();
        decimalSeparator = cfg.getDecimalSeparator();
        posInt = "([+]?\\d+)";
        posDouble = "([+]?\\d*(?:" + decimalSeparator + "\\d+(?:[eE][+-]?\\d+)?)?)";
        dbl = "([+-]?\\d*(?:" + decimalSeparator + "\\d+(?:[eE][+-]?\\d+)?)?)";
    }
    
    abstract public LocationsDto parseData(List<String> rawData) throws IOException;
}

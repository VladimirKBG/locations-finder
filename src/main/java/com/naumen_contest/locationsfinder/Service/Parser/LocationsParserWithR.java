
package com.naumen_contest.locationsfinder.Service.Parser;

import com.naumen_contest.locationsfinder.Config;
import com.naumen_contest.locationsfinder.Service.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationsParserWithR extends LocationsParser {

    public LocationsParserWithR(Config cfg) {
        super(cfg);
    }

    @Override
    public LocationsDTOWithR parseData(List<String> rawData) throws IOException {
        int count = 0;
        double range = 0;
        if (rawData.size() < 1) {
            throw new IOException("Empty data provided to parser.");
        }
        String headersTemplate = "^" + posInt + separator + posDouble;
        Pattern hPattern = Pattern.compile(headersTemplate);
        Matcher hMatcher = hPattern.matcher(rawData.get(0));
        if (hMatcher.matches()) {
            count = Integer.parseInt(hMatcher.group(1));
            range = Double.parseDouble(hMatcher.group(2));
        } else {
            throw new IOException("Can't parse headers line: " + rawData.get(0));
        }
        if (rawData.size() != count + 1) {
            throw new IOException(
                    "Data size not correspond to declared lines count: " 
                            + count + " in headers, but " 
                            + (rawData.size() - 1) + " provided in parser.");
        }
        String template = "^" + dbl + separator + dbl;
        Pattern pattern = Pattern.compile(template);
        List<Location> locations = new ArrayList<>(count);
        double x;
        double y;
        for (int i = 1; i < rawData.size(); i++) {
            Matcher matcher = pattern.matcher(rawData.get(i));
            if (matcher.matches()) {
                x = Double.parseDouble(matcher.group(1));
                y = Double.parseDouble(matcher.group(2));
            } else {
                throw new NumberFormatException("Can't parse data line: " + rawData.get(i));
            }
            locations.add(new Location(x, y));
        }
        return new LocationsDTOWithR(count, range, locations);
    }
    
}

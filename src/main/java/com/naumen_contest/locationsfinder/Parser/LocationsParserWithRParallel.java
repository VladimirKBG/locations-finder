
package com.naumen_contest.locationsfinder.Parser;

import com.naumen_contest.locationsfinder.Config;
import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Model.Location;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
//@Component
public class LocationsParserWithRParallel extends LocationsParserWithR {
    public LocationsParserWithRParallel(Config cfg) {
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
        List<Location> locations = rawData
                .stream()
                .skip(1)
                .parallel()
                .map(x -> {
                    Matcher matcher = pattern.matcher(x);
                    if (matcher.matches()) {
                        return new Location(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)));
                    } else {
                        System.out.println("wrong location: " + x);
                        return new Location(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)));
                    }
                })
                .collect(Collectors.toList());
        return new LocationsDTOWithR(count, range, locations);
    }
}

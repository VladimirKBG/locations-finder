package com.naumen_contest.locationsfinder.Service.Dao;

import com.naumen_contest.locationsfinder.Service.Dto.DtoFactory;
import com.naumen_contest.locationsfinder.Service.Dto.InputLocationsDto;
import com.naumen_contest.locationsfinder.Service.Model.Location;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author user
 */

public class LocationsDaoTxt implements LocationsDao {
    private String separator;
    private String decimalSeparator = "\\.";
    private String posInt = "([+]?\\d+)";
    private String posDouble;
    private String dbl;
    
    Path path;
    

    public LocationsDaoTxt(String path) {
        this.path = Paths.get(path);
        separator = "\\s";
        posDouble = "([+]?\\d*(?:" + decimalSeparator + "\\d+(?:[eE][+-]?\\d+)?)?)";
        dbl = "([+-]?\\d*(?:" + decimalSeparator + "\\d+(?:[eE][+-]?\\d+)?)?)";
    }
    
    @Override
    public InputLocationsDto readInputData(DtoFactory dtoFactory) throws IOException{
        if (!Files.exists(path)) {
            throw new FileNotFoundException(
                "Expected input data file: '" + path.toAbsolutePath() + "'."
            );
        }
        if (!Files.isReadable(path)) {
            throw new AccessDeniedException(
                "File is not readable: '" + path.toAbsolutePath() + "'."
            );
        }
        
        int count = 0;
        double range = 0;
                
        try (BufferedReader  inputData = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String headers = inputData.readLine();
            if (headers == null) {
                throw new IOException("Empty input data file: '" + path.toAbsolutePath() + "'.");
            }
            
            String headersTemplate = "^" + posInt + separator + posDouble;
            Pattern hPattern = Pattern.compile(headersTemplate);
            Matcher hMatcher = hPattern.matcher(headers);
            if (hMatcher.matches()) {
                count = Integer.parseInt(hMatcher.group(1));
                range = Double.parseDouble(hMatcher.group(2));
            } else {
                throw new IOException("Can't parse headers line: " + headers);
            }
            
            String template = "^" + dbl + separator + dbl;
            Pattern pattern = Pattern.compile(template);
            String line;
            List<Location> locations = new ArrayList<>(count);
            while ((line = inputData.readLine()) != null) {
                double x;
                double y;
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    x = Double.parseDouble(matcher.group(1));
                    y = Double.parseDouble(matcher.group(2));
                } else {
                    throw new NumberFormatException("Can't parse data line: " + line);
                }
                locations.add(new Location(x, y));
            }
            return dtoFactory.createLocationsDto(count, range, locations);
        }    
    }
}

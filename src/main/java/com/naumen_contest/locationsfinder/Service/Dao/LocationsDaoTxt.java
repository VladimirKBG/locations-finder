package com.naumen_contest.locationsfinder.Service.Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 *
 * @author user
 */

public class LocationsDaoTxt implements LocationsDAO {
    
    @Override
    public List<String> readFromFile(String fileName) throws IOException{
        Path path = Paths.get(fileName);
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
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}

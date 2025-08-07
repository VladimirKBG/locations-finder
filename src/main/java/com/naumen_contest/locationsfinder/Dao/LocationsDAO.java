package com.naumen_contest.locationsfinder.Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Vladimir Aleksentsev
 */
public interface LocationsDAO {
    public List<String> readFromFile(String fileName) throws IOException;
    
    public void writeToFile(String FileName, List<String> data) throws IOException ;
    
    public List<String> getSupportedFormats();
    
    default Path _getInputPath(String fileName) throws IOException {
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
        return path;
    }
    
    default Path _getOtputPath(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.createFile(path);
        if (!Files.isWritable(path)) {
            throw new AccessDeniedException(
                "File is not writabl-e: '" + path.toAbsolutePath() + "'."
            );
        }
        return path;
    }
}

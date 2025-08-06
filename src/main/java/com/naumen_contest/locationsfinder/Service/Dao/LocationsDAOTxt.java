package com.naumen_contest.locationsfinder.Service.Dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 *
 * @author user
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LocationsDAOTxt implements LocationsDAO {
    static private final List<String> supportedFormats = List.of("txt", "rtf");
    
    @Override
    public List<String> readFromFile(String fileName) throws IOException {
        Path path = _getInputPath(fileName);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    @Override
    public void writeToFile(String fileName, List<String> data) throws IOException {
        Path path = _getOtputPath(fileName);
        
        Files.write(path, data, StandardCharsets.UTF_8);
    }
    
    @Override
    public List<String> getSupportedFormats() {
        return List.copyOf(supportedFormats);
    }
}

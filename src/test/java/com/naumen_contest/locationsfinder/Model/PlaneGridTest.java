
package com.naumen_contest.locationsfinder.Model;

import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class PlaneGridTest {
    
    LocationsDTOWithR dto;
    double delta = 1e-6;
    
    @BeforeEach
    void setup() {
        dto = mock(LocationsDTOWithR.class);
    }
    
    @Test
    void testConstructor_minMax_allPos() {
        List<Location> locs = List.of(
                new Location(10, 20),
                new Location(20, 30),
                new Location(30, 40),
                new Location(50, 60)
        );
        
        when(dto.getLocations()).thenReturn(locs);
        when(dto.getRadius()).thenReturn(20.0);
        
        PlaneGrid planeGrid = new PlaneGrid();
        planeGrid.initializePlaneGrid(dto);
        
        assertEquals(10d, (double) getFieldValue(planeGrid, "xMin"), delta);
        assertEquals(50d, (double) getFieldValue(planeGrid, "xMax"), delta);
        assertEquals(20d, (double) getFieldValue(planeGrid, "yMin"), delta);
        assertEquals(60d, (double) getFieldValue(planeGrid, "yMax"), delta);

    }
    
    @Test
    void testConstructor_singleLocation() {
        List<Location> locs = List.of(
                new Location(10, 20)
        );
        when(dto.getLocations()).thenReturn(locs);
        when(dto.getRadius()).thenReturn(20.0);
        
        PlaneGrid planeGrid = new PlaneGrid();
        planeGrid.initializePlaneGrid(dto);
        assertEquals(Double.MAX_VALUE, (double) getFieldValue(planeGrid, "dx"), delta);
        assertEquals(Double.MAX_VALUE, (double) getFieldValue(planeGrid, "dy"), delta);
        
        assertEquals(1, getFieldValue(planeGrid, "sizeX"));
        assertEquals(1, getFieldValue(planeGrid, "sizeY"));
    }
    
     @Test
    void testAddEntity_validCoordinates_entityIsAddedToCorrectCell() throws Exception {
        // Настраиваем DTO для создания сетки
        List<Location> locs = List.of(
            new Location(0, 0),
            new Location(100, 100)
        );
        when(dto.getLocations()).thenReturn(locs);
        when(dto.getRadius()).thenReturn(10.0);
        
        PlaneGrid planeGrid = new PlaneGrid();
        planeGrid.initializePlaneGrid(dto);
        
        // Добавляем новую локацию
        Location newLoc = new Location(15.0, 15.0);
        planeGrid.addEntity(newLoc, 15.0, 15.0);

        // Получаем доступ к приватному полю grid
        List<List<List<Location>>> grid = (List<List<List<Location>>>) getFieldValue(planeGrid, "grid");
        
        // Получаем ячейку, в которую должна быть добавлена локация
        // dx = (100 - 0) / ( (100 - 0) / 10) = 10.0
        // dy = (100 - 0) / ( (100 - 0) / 10) = 10.0
        // _getXRow(15) = (int)((15-0)/10 + 1) = (int)(1.5 + 1) = 2
        // _getYRow(15) = (int)((15-0)/10 + 1) = (int)(1.5 + 1) = 2
        
        List<Location> cell = grid.get(1).get(1);
        
        // Проверяем, что ячейка содержит нашу новую локацию
        assertTrue(cell.contains(newLoc));
    }
    
    @Test
    void testGetNearestCells_centralCoordinates_returnsNineCells() {
        // Создаем сетку
        List<Location> locs = List.of(
            new Location(0, 0),
            new Location(100, 100)
        );
        when(dto.getLocations()).thenReturn(locs);
        when(dto.getRadius()).thenReturn(10.0);
        PlaneGrid planeGrid = new PlaneGrid();
        planeGrid.initializePlaneGrid(dto);

        // Добавляем локации для наполнения сетки
        planeGrid.addEntity(new Location(45, 45), 45, 45); // xRow=5, yRow=5
        planeGrid.addEntity(new Location(55, 55), 55, 55); // xRow=6, yRow=6
        
        // Получаем ближайшие ячейки для центральной точки (50, 50)
        List<List<Location>> nearestCells = planeGrid.getNearestCells(50, 50);

        // Проверяем, что возвращается 9 ячеек
        assertEquals(9, nearestCells.size());
    }
    
    @Test
    void testGetNearestCells_edgeCoordinates_returnsFewerThanNineCells() {
        // Создаем сетку
        List<Location> locs = List.of(
            new Location(0, 0),
            new Location(100, 100)
        );
        when(dto.getLocations()).thenReturn(locs);
        when(dto.getRadius()).thenReturn(10.0);
        PlaneGrid planeGrid = new PlaneGrid();
        planeGrid.initializePlaneGrid(dto);
        
        // Получаем ближайшие ячейки для точки в углу (0, 0)
        List<List<Location>> nearestCells = planeGrid.getNearestCells(0, 0);
        assertEquals(4, nearestCells.size());
        
        // Проверяем точку на другом краю
        nearestCells = planeGrid.getNearestCells(100, 100);
        for (var cell : nearestCells) {
            System.out.println(cell.size());
        }
        assertEquals(4, nearestCells.size());
        
        // Проверяем точку на границе, но не в углу
        nearestCells = planeGrid.getNearestCells(50, 100);
        // (xRow=6, yRow=1) -> 3x2 = 6 ячеек
        assertEquals(6, nearestCells.size());
    }
    
    @Test
    void testPrivateGetXRowMethod_usesReflection_validCalculation() throws Exception {
        // Создаем сетку
        List<Location> locs = List.of(
            new Location(0, 0),
            new Location(100, 100)
        );
        when(dto.getLocations()).thenReturn(locs);
        when(dto.getRadius()).thenReturn(10.0);
        PlaneGrid planeGrid = new PlaneGrid();
        planeGrid.initializePlaneGrid(dto);
        
        // Используем рефлексию для вызова приватного метода
        Method method = PlaneGrid.class.getDeclaredMethod("_getXRow", double.class);
        method.setAccessible(true);
        
        // Проверяем вычисление для разных значений x
        assertEquals(0, (int) method.invoke(planeGrid, 0.0));
        assertEquals(5, (int) method.invoke(planeGrid, 50.0));
        assertEquals(10, (int) method.invoke(planeGrid, 100.0));
    }
    
    private Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail("Can not obtain accsess to field: " + fieldName + " " + e.getMessage());
            return 0d;
        }
    }
}


package com.naumen_contest.locationsfinder.Model;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class LocationTest {
    private int baseId = 0;
    
    @BeforeEach
    void setBaseId() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field countField = Location.class.getDeclaredField("count");
        countField.setAccessible(true);
        baseId = countField.getInt(null);
    }
    
    @Test
    void createFixedLocations() {
        System.out.println("Start test createFixedLocations");
        Location loc1 = new Location(0.1d, 0.1d);
        Assertions.assertEquals(0.1d, loc1.X());
        Assertions.assertEquals(0.1d, loc1.Y());
        Assertions.assertEquals(0 + baseId, loc1.Id());
        Location loc2 = new Location(0d, -0.1d);
        Assertions.assertEquals(0d, loc2.X());
        Assertions.assertEquals(-0.1d, loc2.Y());
        Assertions.assertEquals(1 + baseId, loc2.Id());
        Location loc3 = new Location(-0.1d, 0d);
        Assertions.assertEquals(-0.1d, loc3.X());
        Assertions.assertEquals(0d, loc3.Y());
        Assertions.assertEquals(2 + baseId, loc3.Id());
    }
    
    @Test
    void createRandomLocations() {
        System.out.println("Start test createRandomLocations");
        int locCount = 10000;
        double bound = 1.e7;
        Location loc;
        for (int i = 0; i < locCount; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-bound, bound);
            double y = ThreadLocalRandom.current().nextDouble(-bound, bound);
            loc = new Location(x, y);
            Assertions.assertEquals(x, loc.X());
            Assertions.assertEquals(y, loc.Y());
            Assertions.assertEquals(i + baseId, loc.Id());
        }
    }

}

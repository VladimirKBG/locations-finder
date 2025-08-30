
package com.naumen_contest.locationsfinder.util;

import com.naumen_contest.locationsfinder.Dto.LocationsDTOWithR;
import com.naumen_contest.locationsfinder.Model.Location;
import com.naumen_contest.locationsfinder.Model.PlaneGrid;
import com.naumen_contest.locationsfinder.Strategy.N2NeighborsCountingStrategy;
import com.naumen_contest.locationsfinder.Strategy.NeighborsCountingStrategy;
import com.naumen_contest.locationsfinder.Strategy.PlaneGridNeighborsCountingStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Created by Vladimir Aleksentsev, 2025
 */
public class StrategiesComparator {
    public static void main(String[] args) {
        compareNeighborsCountingStrategies(500_000, 100);
    }
    
    static void compareNeighborsCountingStrategies(int count, double r) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        List<Location> locs = new ArrayList<>(count);
        double limit = count/100 + 100;
        for (int i = 0; i < count; i++) {
            locs.add(new Location(rng.nextDouble(-limit, limit), rng.nextDouble(-limit, limit)));
        }
        
        LocationsDTOWithR dto = new LocationsDTOWithR(count, r, locs);
        N2NeighborsCountingStrategy n2 = new N2NeighborsCountingStrategy();
        PlaneGridNeighborsCountingStrategy pg = new PlaneGridNeighborsCountingStrategy(new PlaneGrid());
        
        CompletableFuture<Long> t1 = CompletableFuture.supplyAsync(() -> countNeighbors(n2, dto));
        CompletableFuture<Long> t2 = CompletableFuture.supplyAsync(() -> countNeighbors(pg, dto));
        

        System.out.println("n2 time is " + t1.join() + "\nPG time is " + t2.join());
        
    }
    
    static Long countNeighbors(NeighborsCountingStrategy s, LocationsDTOWithR dto) {
        long start = System.currentTimeMillis();
        s.countNeighbors(dto);
        return System.currentTimeMillis() - start;
    }
}

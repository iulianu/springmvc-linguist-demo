package com.iuliandogariu.linguist;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public final class TopCountsTest {

    @Test
    public void shouldSortAndReturnTopCounts() {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("USA", 144);
        counts.put("Germany", 90);
        counts.put("Russia", 1230);
        counts.put("Australia", 4);
        List<Map.Entry<String, Integer>> topCounts = TopCounts.ofMap(counts, 2);
        assertEquals(2, topCounts.size());
        assertEquals("Russia", topCounts.get(0).getKey());
        assertEquals(1230, topCounts.get(0).getValue().intValue());
        assertEquals("USA", topCounts.get(1).getKey());
        assertEquals(144, topCounts.get(1).getValue().intValue());
    }
}

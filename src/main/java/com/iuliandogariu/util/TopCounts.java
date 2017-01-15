package com.iuliandogariu.util;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class TopCounts {

    /**
     * Takes a map of counts with an arbitrary key type K,
     * and returns a list of pairs of key+count, sorted
     * in descending order according to the count.
     *
     * @param limit Max number of entries to return,
     *            in descending order.
     * @return list of keys plus their count
     */
    public static <K> List<Map.Entry<K, Long>> ofMap(Map<K, Long> counts, int limit) {
        return counts.entrySet().stream()
                .sorted(reverseOrder(comparing(Map.Entry::getValue)))
                .limit(limit)
                .collect(Collectors.toList());
    }
}

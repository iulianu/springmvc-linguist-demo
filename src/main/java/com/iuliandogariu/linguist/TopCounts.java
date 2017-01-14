package com.iuliandogariu.linguist;

import java.util.*;
import java.util.stream.Collectors;

public class TopCounts {

    /**
     * Takes a map of counts with an arbitrary key type K,
     * and returns a list of pairs of key+count, sorted
     * in descending order according to the count.
     *
     * @param max Max number of entries to return,
     *            in descending order.
     * @return list of keys plus their count
     */
    public static <K> List<Map.Entry<K, Integer>> ofMap(Map<K, Integer> counts, int max) {
        List<Map.Entry<K, Integer>> entries = new ArrayList<>(counts.entrySet());
        entries.sort(Collections.reverseOrder(Comparator.comparing(Map.Entry::getValue)));
        List<Map.Entry<K, Integer>> topEntries = entries;
        if(entries.size() > max) {
            topEntries = entries.subList(0, max);
        }
        return topEntries
                .stream()
                .collect(Collectors.toList());
    }
}

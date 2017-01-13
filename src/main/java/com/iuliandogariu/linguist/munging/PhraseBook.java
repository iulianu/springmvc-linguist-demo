package com.iuliandogariu.linguist.munging;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Models an in-memory collection of PhrasePairs.
 */
public class PhraseBook {

    private List<PhrasePair> pairs = new ArrayList<>();

    public void add(PhrasePair pair) {
        pairs.add(pair);
    }

    public String toMungedString() {
        return pairs.stream().map(PhrasePair::toMungedString).collect(Collectors.joining(" "));
    }

}

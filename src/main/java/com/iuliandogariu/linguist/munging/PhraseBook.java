package com.iuliandogariu.linguist.munging;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PhraseBook {

    /** Joins phrase pairs in munged form */
    public static Collector<CharSequence, ?, String> mungedCollector() {
        return Collectors.joining(" ");
    }

}

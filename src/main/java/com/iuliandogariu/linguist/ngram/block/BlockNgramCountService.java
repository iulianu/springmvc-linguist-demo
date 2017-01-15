package com.iuliandogariu.linguist.ngram.block;

import com.iuliandogariu.linguist.ngram.Ngram;

import java.util.List;
import java.util.Map;

/**
 * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text.
 *
 * This service works on text in memory, represented as String.
 */
public interface BlockNgramCountService {

    /**
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     */
    List<Map.Entry<Ngram, Long>> countNgrams(int maxNgramCount, String text);

}

package com.iuliandogariu.linguist.ngram.sequential;

import com.iuliandogariu.linguist.ngram.Ngram;

import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text.
 *
 * This service works on text streams as java.io.Reader instances.
 */
public interface SequentialNgramCountService {

    /**
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     */
    List<Map.Entry<Ngram, Long>> countNgramsInStream(int maxNgramCount, Reader textReader);

}

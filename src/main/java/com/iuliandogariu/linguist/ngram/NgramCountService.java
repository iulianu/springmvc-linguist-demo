package com.iuliandogariu.linguist.ngram;

import com.iuliandogariu.linguist.TextStream;
import com.iuliandogariu.linguist.TopCounts;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Stream;

/**
 * The two public service methods
 * do the same thing, but one of them works on text streams.
 */
@Service
public class NgramCountService {

    /**
     * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text.
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     */
    public List<Map.Entry<Ngram, Integer>> countNgrams(int maxNgramCount, String text) {
        return countNgrams(maxNgramCount, new StringReader(text));
    }

    /**
     * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text stream.
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     */
    public List<Map.Entry<Ngram, Integer>> countNgrams(int maxNgramCount, Reader textReader) {
        Map<Ngram, Integer> counts = tokens(textReader)
                .collect(NgramCounter::new, NgramCounter::accept, NgramCounter::combine)
                .getCounts();
        return TopCounts.ofMap(counts, maxNgramCount);
    }

    /** Transform a stream of text into a stream of tokens */
    Stream<String> tokens(Reader textReader) {
        return TextStream.ofReaderWithDelimiter(textReader, "\\W+");
    }

}

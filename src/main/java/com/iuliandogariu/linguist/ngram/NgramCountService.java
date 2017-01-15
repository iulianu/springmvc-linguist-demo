package com.iuliandogariu.linguist.ngram;

import com.iuliandogariu.linguist.SequentialTokenStream;
import com.iuliandogariu.linguist.TopCounts;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;

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
     *
     * If you have an InputStream instead of a String, see the other overload.
     */
    public List<Map.Entry<Ngram, Long>> countNgrams(int maxNgramCount, String text) {
        Map<Ngram, Long> counts = StreamSupport.stream(
                new ParallelNgramSpliterator(text),
                true)
                .collect(Collectors.groupingByConcurrent(identity(), counting()));
        return TopCounts.ofMap(counts, maxNgramCount);
    }

    /**
     * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text stream.
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     *
     * Call this service method if you have an InputStream, e.g. from a network socket,
     * as opposed to the full text in memory.
     */
    public List<Map.Entry<Ngram, Long>> countNgramsInStream(int maxNgramCount, Reader textReader) {
        Map<Ngram, Long> counts = SequentialTokenStream.ofReaderWithDelimiter(textReader, "\\W+")
                .collect(SequentialNgramCounterConsumer::new, SequentialNgramCounterConsumer::accept, SequentialNgramCounterConsumer::combine)
                .getCounts();
        return TopCounts.ofMap(counts, maxNgramCount);
    }

}

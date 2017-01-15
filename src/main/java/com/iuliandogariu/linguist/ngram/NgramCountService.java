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
 * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text.
 *
 * Two service methods are exposed:
 *  - countNgrams() accepts a String and performs the count in parallel
 *  - countNgramsInStream() accepts a Reader and performs the count sequentially.
 */
@Service
public class NgramCountService {

    /**
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     *
     * If you have an InputStream instead of a String, see countNgramsInStream().
     */
    public List<Map.Entry<Ngram, Long>> countNgrams(int maxNgramCount, String text) {
        Map<Ngram, Long> counts = StreamSupport.stream(
                    new ParallelNgramSpliterator(text), true)
                .collect(Collectors.groupingByConcurrent(identity(), counting()));
        return TopCounts.ofMap(counts, maxNgramCount);
    }

    /**
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     *
     * Call this service method if you have an InputStream, e.g. from a network socket,
     * as opposed to the full text in memory.
     */
    public List<Map.Entry<Ngram, Long>> countNgramsInStream(int maxNgramCount, Reader textReader) {
        Map<Ngram, Long> counts = SequentialTokenStream.ofReaderWithDelimiter(textReader, Ngram.DELIMITER_PATTERN)
                .collect(SequentialNgramCounterConsumer::new, SequentialNgramCounterConsumer::accept, SequentialNgramCounterConsumer::combine)
                .getCounts();
        return TopCounts.ofMap(counts, maxNgramCount);
    }

}

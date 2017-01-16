package com.iuliandogariu.linguist.ngram.sequential;

import com.iuliandogariu.linguist.ngram.Ngram;
import com.iuliandogariu.util.TopCounts;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text.
 *
 * This service works on text streams as java.io.Reader instances.
 *
 * The implementation uses Java 8 Streams. It uses a Scanner to
 * scan the Reader for tokens and place them in a Stream, then uses
 * a custom Consumer to count up the N-grams.
 * The process is sequential by nature.
 */
@Service
public class JavaStreamsSequentialNgramCountService implements SequentialNgramCountService {

    /**
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     *
     * Call this service method if you have an InputStream, e.g. from a network socket,
     * as opposed to the full text in memory.
     *
     * The call to this service method blocks until the input Reader reaches EOF.
     */
    public List<Map.Entry<Ngram, Long>> countNgramsInStream(int maxNgramCount, Reader textReader) {
        Map<Ngram, Long> counts = SequentialTokenStream.ofReader(textReader)
                .collect(SequentialNgramCounterConsumer::new, SequentialNgramCounterConsumer::accept, SequentialNgramCounterConsumer::combine)
                .getCounts();
        return TopCounts.ofMap(counts, maxNgramCount);
    }

}

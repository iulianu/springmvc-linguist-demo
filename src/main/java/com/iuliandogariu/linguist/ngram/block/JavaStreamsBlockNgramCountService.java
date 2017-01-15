package com.iuliandogariu.linguist.ngram.block;

import com.iuliandogariu.linguist.ngram.Ngram;
import com.iuliandogariu.util.TopCounts;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;

/**
 * Counts N-grams (1-grams, 2-grams and 3-grams) occurring in the specified text.
 *
 * This service works on text in memory, represented as String.
 *
 * The implementation uses Java 8 Streams. It uses a custom
 * Spliterator to split the String in smaller work chunks and
 * do the work in parallel.
 */
@Service
public class JavaStreamsBlockNgramCountService implements BlockNgramCountService {

    /**
     * @return list of Ngram counts in descending order of count,
     * up to a maximum of maxNgramCount.
     */
    @Override
    public List<Map.Entry<Ngram, Long>> countNgrams(int maxNgramCount, String text) {
        Map<Ngram, Long> counts = StreamSupport.stream(
                    new ParallelNgramSpliterator(text), true)
                .collect(Collectors.groupingByConcurrent(identity(), counting()));
        return TopCounts.ofMap(counts, maxNgramCount);
    }

}

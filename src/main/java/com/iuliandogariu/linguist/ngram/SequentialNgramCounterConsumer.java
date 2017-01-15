package com.iuliandogariu.linguist.ngram;

import java.util.*;
import java.util.function.Consumer;

/**
 * Consumes String tokens from a sequential stream, and
 * updates N-gram counts as it goes.
 *
 * This consumer is not parallelizable.
 */
class SequentialNgramCounterConsumer implements Consumer<String> {
    private Map<Ngram, Long> counts = new HashMap<>();

    public Map<Ngram, Long> getCounts() { return counts; }

    private TokenMemory tokenMemory = new TokenMemory();

    @Override
    public void accept(String currentToken) {
        for(Ngram ngram : tokenMemory.acceptToken(currentToken)) {
            counts.merge(ngram, 1L, Long::sum);
        }
    }

    /**
     * Combiner method provided, but never expected to be called,
     * because this collector works only on sequential streams.
     */
    public void combine(SequentialNgramCounterConsumer other) {
        other.getCounts().forEach((k, v) -> counts.merge(k, v, Long::sum));
    }
}

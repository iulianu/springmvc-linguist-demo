package com.iuliandogariu.linguist.ngram.sequential;

import com.iuliandogariu.linguist.ngram.CircularBufferTokenMemory;
import com.iuliandogariu.linguist.ngram.Ngram;
import com.iuliandogariu.linguist.ngram.TokenMemory;

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

    private TokenMemory tokenMemory;

    public SequentialNgramCounterConsumer() {
        this(new CircularBufferTokenMemory());
    }

    public SequentialNgramCounterConsumer(TokenMemory tokenMemory) {
        this.tokenMemory = tokenMemory;
    }

    @Override
    public void accept(String currentToken) {
        for(Ngram ngram : tokenMemory.acceptToken(currentToken)) {
            counts.merge(ngram, 1L, Long::sum);
        }
    }

    /**
     * This collector works only on sequential streams.
     */
    public void combine(SequentialNgramCounterConsumer other) {
        throw new AssertionError("No combiners allowed on SequentialNgramCounterConsumer");
        //other.getCounts().forEach((k, v) -> counts.merge(k, v, Long::sum));
    }
}

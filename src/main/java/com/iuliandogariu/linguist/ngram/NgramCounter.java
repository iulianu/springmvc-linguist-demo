package com.iuliandogariu.linguist.ngram;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.*;
import java.util.function.Consumer;

/**
 * Consumes Strings from a stream, constructs N-grams of orders 1 to 3, and
 * updates counts.
 *
 * This consumer only works when token sequence is guaranteed, so
 * it is not parallelizable.
 */
class NgramCounter implements Consumer<String> {
    private Map<Ngram, Integer> counts = new HashMap<>();

    public Map<Ngram, Integer> getCounts() { return counts; }

    /** We emit 1-grams, 2-grams and 3-grams only */
    public static final int MAX_ORDER = 3;

    /** Hold the past N-1 tokens that I have seen */
    private CircularFifoQueue<String> tokenBuffer = new CircularFifoQueue<>(MAX_ORDER - 1);

    @Override
    public void accept(String currentToken) {
        for(int order = 1; order <= MAX_ORDER; order++) {
            if(! enoughTokens(order) )
                break;
            acceptNgram(new Ngram(order, joinedNgramTokens(order, currentToken)));
        }
        tokenBuffer.add(currentToken);
    }

    private void acceptNgram(Ngram ngram) {
        counts.merge(ngram, 1, Integer::sum);
    }

    /** I just started consuming and may not have enough tokens for a 3-gram for example.*/
    private boolean enoughTokens(int order) {
        return tokenBuffer.size() >= order - 1;
    }

    /**
     * Pull tokens from the token buffer to construct an N-gram of
     * the given order.
     */
    private String joinedNgramTokens(int order, String currentToken) {
        int tokensNeededFromBuffer = order - 1;
        final String DELIMITER = " ";
        String[] tokens = new String[order];
        for(int i = 0; i < tokensNeededFromBuffer; i++) {
            tokens[i] = tokenBuffer.get(tokenBuffer.size() - tokensNeededFromBuffer + i);
        }
        tokens[tokensNeededFromBuffer] = currentToken;
        return String.join(DELIMITER, tokens);
    }

    public void combine(NgramCounter other) {
        throw new RuntimeException("This consumer may not be used with parallel streams!");
        // If this were a safe operation, you would combine the counters like so:
        //other.getCounts().forEach((k, v) -> counts.merge(k, v, Integer::sum));
    }

}

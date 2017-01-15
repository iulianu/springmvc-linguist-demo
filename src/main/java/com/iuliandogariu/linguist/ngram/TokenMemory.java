package com.iuliandogariu.linguist.ngram;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.ArrayList;
import java.util.Collection;

public class TokenMemory {
    /** By default we emit 1-grams, 2-grams and 3-grams */
    public static final int DEFAULT_MAX_ORDER = 3;

    private int maxOrder;

    /** Hold the past N-1 tokens that I have seen */
    private CircularFifoQueue<String> pastTokens;

    public TokenMemory() {
        this(DEFAULT_MAX_ORDER);
    }

    public TokenMemory(int maxOrder) {
        this.maxOrder = maxOrder;
        pastTokens = new CircularFifoQueue<>(maxOrder - 1);
    }

    public void initFromSnapshot(String[] snapshot) {
        pastTokens.clear();
        for(String token: snapshot)
            pastTokens.add(token);
    }

    public String[] snapshot() {
        return pastTokens.toArray(new String[]{});
    }

    /**
     * @param currentToken token seen just now
     * @return all Ngrams that can be formed with this token and the tokens
     * in the circular buffer.
     */
    public Collection<Ngram> acceptToken(String currentToken) {
        Collection<Ngram> ngrams = new ArrayList<>();
        for(int order = 1; order <= maxOrder; order++) {
            if(! enoughTokens(order) )
                break;
            ngrams.add(new Ngram(order, joinedNgramTokens(order, currentToken)));
        }
        pastTokens.add(currentToken);
        return ngrams;
    }

    public int getMaxOrder() { return maxOrder; }

    public boolean isAtFullCapacity() {
        return pastTokens.isAtFullCapacity();
    }

    /** I just started consuming and may not have enough tokens for a 3-gram for example.*/
    private boolean enoughTokens(int order) {
        return pastTokens.size() >= order - 1;
    }

    /**
     * Pull tokens from the token buffer to construct an N-gram of
     * the given order.
     */
    String joinedNgramTokens(int order, String currentToken) {
        int tokensNeededFromBuffer = order - 1;
        final String DELIMITER = " ";
        String[] tokens = new String[order];
        for(int i = 0; i < tokensNeededFromBuffer; i++) {
            tokens[i] = pastTokens.get(pastTokens.size() - tokensNeededFromBuffer + i);
        }
        tokens[tokensNeededFromBuffer] = currentToken;
        return String.join(DELIMITER, tokens);
    }

}

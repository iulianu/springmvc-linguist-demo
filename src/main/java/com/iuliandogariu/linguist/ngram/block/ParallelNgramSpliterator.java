package com.iuliandogariu.linguist.ngram.block;

import com.iuliandogariu.linguist.ngram.CircularBufferTokenMemory;
import com.iuliandogariu.linguist.ngram.Ngram;
import com.iuliandogariu.util.stream.ParallelTokenizingSpliterator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.MatchResult;

/**
 * A Spliterator that works over a String and emits N-grams.
 * It tries to split the work in parallel, with chunks of text of size
 * approximately equal to maxSplitSize characters.
 *
 * This spliterator works with a TokenMemory instance to remember
 * the past N-1 tokens.
 *
 * When trying to split at a given position, the spliterator will scan
 * ahead in the string in order to populate the token memory.
 * This is why splits are only _approximately_ half-way.
 */
public class ParallelNgramSpliterator extends ParallelTokenizingSpliterator<Ngram> {

    private CircularBufferTokenMemory tokenMemory = new CircularBufferTokenMemory();

    public ParallelNgramSpliterator(String text) {
        this(text, DEFAULT_SPLIT_SIZE, new String[]{});
    }

    /**
     *
     * @param text text to work on
     * @param maxSplitSize hint about the size of a chunk of text, in number of characters
     */
    public ParallelNgramSpliterator(String text, int maxSplitSize) {
        this(text, maxSplitSize, new String[]{});
    }

    /**
     *
     * @param text text to work on
     * @param maxSplitSize hint about the size of a chunk of text, in number of characters
     * @param pastTokens tokens with which to populate the token memory,
     *                   this parameter is non-empty after the Spliterator has split in two.
     */
    public ParallelNgramSpliterator(String text, int maxSplitSize, String[] pastTokens) {
        super(text, maxSplitSize, Ngram.DELIMITER_PATTERN);
//        System.err.printf("Split %d %s\n", text.length(), text);
        tokenMemory.initFromSnapshot(pastTokens);
    }

    @Override
    protected void acceptToken(Consumer<? super Ngram> action, String currentToken) {
        for(Ngram ngram : tokenMemory.acceptToken(currentToken)) {
            action.accept(ngram);
        }
    }

    @Override
    protected Pair<Spliterator<Ngram>, Integer> trySplitAtTokenBoundary(int currentChar, int referencePos) {
        final Pair<Spliterator<Ngram>, Integer> NULL_RESULT = Pair.of(null, text.length());

        CircularBufferTokenMemory tempTokenMemory = new CircularBufferTokenMemory(tokenMemory.getMaxOrder());
        // Scan tokens in advance
        int splitPos = referencePos;
        while(!tempTokenMemory.isAtFullCapacity()) {
            MatchResult delimResult = findNextDelimiter(splitPos);
            if( delimResult != null ) {
                if(delimResult.start() > splitPos) {
                    tempTokenMemory.acceptToken(text.substring(splitPos, delimResult.start()));
                }
                splitPos = delimResult.end();
            } else {
                // Don't split if near the end.
                return NULL_RESULT;
            }
        }
//                System.err.printf("Trying split, split position %d\n", splitPos);

        if(splitPos >= text.length())
            return NULL_RESULT;

        // Let the new spliterator continue from this position
        String[] pastTokensSnapshot = tokenMemory.snapshot();
//        System.err.printf("X Splitting %d-%d\n", currentChar, splitPos);
        Spliterator<Ngram> spliterator =
                new ParallelNgramSpliterator(text.substring(currentChar, splitPos), maxSplitSize, pastTokensSnapshot);

        // Advance our position and reset the token memory
        tokenMemory.initFromSnapshot(tempTokenMemory.snapshot());
        return Pair.of(spliterator, splitPos);
    }
}

package com.iuliandogariu.util.stream;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Spliterator template that works over a String and emits values
 * of type T based on String tokens.
 * Tokens are scanned using the specified delimiter pattern.
 *
 * This abstract spliterator lets the implementer classes control
 * where to split. This is why splits are only _approximately_ half-way.
 */
public abstract class ParallelTokenizingSpliterator<T> implements Spliterator<T> {

    protected static final int DEFAULT_SPLIT_SIZE = 10_000;
    protected final String text;
    protected int maxSplitSize = 0;
    private int currentChar = 0;
    private Matcher delimiterMatcher;

    protected ParallelTokenizingSpliterator(String text, int maxSplitSize, Pattern delimiterPattern) {
        this.maxSplitSize = maxSplitSize;
//        System.err.printf("Split %d %s\n", text.length(), text);
        this.text = text;
        delimiterMatcher = delimiterPattern.matcher(text);
    }

    @Override
    public final long estimateSize() {
        return text.length() - currentChar;
    }

    @Override
    public final int characteristics() {
        return ORDERED + NONNULL + IMMUTABLE + SIZED + SUBSIZED;
    }

    @Override
    public final boolean tryAdvance(Consumer<? super T> action) {
        if (currentChar >= text.length())
            return false;

        MatchResult delimResult = null;
        if( currentChar < text.length() - 1 ) {
            delimResult = findNextDelimiter(currentChar + 1);
        }

        if (delimResult != null) {
            if (delimResult.start() > currentChar) {
                acceptToken(action, text.substring(currentChar, delimResult.start()));
            }
            currentChar = delimResult.end();
            return true;
        } else {
            acceptToken(action, text.substring(currentChar));
            currentChar = text.length();
            return false;
        }
    }

    protected abstract void acceptToken(Consumer<? super T> action, String currentToken);

    protected abstract Pair<Spliterator<T>, Integer> trySplitAtTokenBoundary(int currentChar, int referencePos);

    @Override
    public final Spliterator<T> trySplit() {
        int currentSize = text.length() - currentChar;
        if (currentSize < maxSplitSize)
            return null;

        for (int referencePos = currentSize / 2 + currentChar;
             referencePos >= 0; referencePos--) {
            // Find the previous potential token boundary
            if (Character.isWhitespace(text.charAt(referencePos))) {
//                System.err.printf("Trying split, reference position %d\n", referencePos);
                Pair<Spliterator<T>, Integer> splitResult = trySplitAtTokenBoundary(currentChar, referencePos);
                Spliterator<T> newSpliterator = splitResult.getLeft();
                int splitPos = splitResult.getRight();
                if (newSpliterator == null)
                    return null;
                if (splitPos >= text.length())
                    return null;

                // Let the new spliterator continue from the current position.
                // We are jumping ahead.
                currentChar = splitPos;
                return newSpliterator;
            }
        }
        return null;
    }

    protected final MatchResult findNextDelimiter(int pos) {
        if (delimiterMatcher.find(pos)) {
            return delimiterMatcher.toMatchResult();
        } else {
            return null;
        }
    }
}

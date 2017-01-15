package com.iuliandogariu.linguist.munging.block;

import com.iuliandogariu.linguist.munging.PhrasePair;
import com.iuliandogariu.util.stream.ParallelTokenizingSpliterator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.MatchResult;

import static com.iuliandogariu.linguist.munging.PhrasePair.UNMUNGED_FORMAT_DELIMITER;

/**
 * A Spliterator that works over an unmunged String and emits Phrase pairs.
 */
public class ParallelPhrasePairSpliterator extends ParallelTokenizingSpliterator<PhrasePair> {

    public ParallelPhrasePairSpliterator(String text) {
        this(text, DEFAULT_SPLIT_SIZE);
    }

    /**
     *
     * @param text text to iterate on
     * @param maxSplitSize hint about the size of a chunk of text, in numbers of characters
     */
    public ParallelPhrasePairSpliterator(String text, int maxSplitSize) {
        super(text, maxSplitSize, UNMUNGED_FORMAT_DELIMITER);
        System.err.printf("Split %d %s\n", text.length(), text);
    }

    @Override
    protected void acceptToken(Consumer<? super PhrasePair> action, String currentToken) {
        action.accept(PhrasePair.fromUnmungedString(currentToken));
    }

    @Override
    protected Pair<Spliterator<PhrasePair>, Integer> trySplitAtTokenBoundary(int currentChar, int referencePos) {
        final Pair<Spliterator<PhrasePair>, Integer> NULL_RESULT = Pair.of(null, text.length());

        MatchResult delimResult = findNextDelimiter(referencePos);
        if( delimResult != null ) {
            int splitPos = delimResult.end();
            Spliterator<PhrasePair> spliterator =
                    new ParallelPhrasePairSpliterator(text.substring(currentChar, splitPos), maxSplitSize);
            return Pair.of(spliterator, splitPos);
        } else {
            // Don't split if near the end.
            return NULL_RESULT;
        }
    }
}


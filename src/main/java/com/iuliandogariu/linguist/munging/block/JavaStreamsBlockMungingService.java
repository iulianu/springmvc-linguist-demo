package com.iuliandogariu.linguist.munging.block;

import com.iuliandogariu.linguist.munging.PhraseBook;
import com.iuliandogariu.linguist.munging.PhrasePair;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

/**
 * Returns munged representations of phrase pairs passed in
 * unmunged format.
 *
 * This service works on text in memory, represented as String.
 *
 * The implementation uses Java 8 Streams. It uses a custom
 * Spliterator to split the String in smaller work chunks and
 * do the work in parallel.
 */
@Service
public class JavaStreamsBlockMungingService implements BlockMungingService {

    /**
     * @param unmungedText the unmunged form of the phrase pairs
     * @return munged representation of all the phrase pairs in the text.
     */
    @Override
    public String mungedPhrasePairs(String unmungedText) {
        return StreamSupport.stream(
                    new ParallelPhrasePairSpliterator(unmungedText),true)
                .map(PhrasePair::toMungedString)
                .collect(PhraseBook.mungedCollector());
    }

}

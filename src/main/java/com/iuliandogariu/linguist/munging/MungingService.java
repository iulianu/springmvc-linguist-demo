package com.iuliandogariu.linguist.munging;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.stream.StreamSupport;

/**
 * Returns munged representations of phrase pairs passed in
 * unmunged format.
 * Two service methods are exposed:
 *  - mungedPhrasePairs() accepts a String and munges the phrase pairs in parallel
 *  - mungedPhrasePairsFromStream() accepts a Reader and munges
 *    the phrase pairs sequentially.
 */
@Service
public class MungingService {

    /**
     * Call this service method if you have a text with the full request text.
     *
     * @param unmungedText the unmunged form of the phrase pairs
     * @return a representation of all the phrase pairs in the text.
     */
    public String mungedPhrasePairs(String unmungedText) {
        return StreamSupport.stream(
                    new ParallelPhrasePairSpliterator(unmungedText),true)
                .map(PhrasePair::toMungedString)
                .collect(PhraseBook.mungedCollector());
    }

    /**
     * Call this service method if you have a stream with the request text,
     * e.g. from a network socket.
     *
     * @param unmungedTextReader the unmunged form of the phrase pairs, as a Reader
     * @return a representation of all the phrase pairs in the text.
     */
    public String mungedPhrasePairsFromStream(Reader unmungedTextReader) {
        return SequentialPhrasePairStream.fromUnmunged(unmungedTextReader)
                .map(PhrasePair::toMungedString)
                .collect(PhraseBook.mungedCollector());
    }

}

package com.iuliandogariu.linguist.munging;

import com.iuliandogariu.linguist.SequentialTokenStream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.iuliandogariu.linguist.munging.PhrasePair.UNMUNGED_FORMAT_DELIMITER;

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
        return phrasePairStream(unmungedTextReader)
                .map(PhrasePair::toMungedString)
                .collect(PhraseBook.mungedCollector());
    }

    private Stream<PhrasePair> phrasePairStream(Reader unmungedReader) {
        return unmungedPhraseStream(unmungedReader).map(PhrasePair::fromUnmungedString);
    }

    /**
     * Scans the text for the delimiter of two concatenated phrase pairs
     * in unmunged form.
     *
     * @param unmungedReader a Reader that gives access to the unmunged text form
     *                       of the phrase pairs
     * @return a Stream of Strings each representing a single phrase pair.
     */
    Stream<String> unmungedPhraseStream(Reader unmungedReader) {
        return SequentialTokenStream.ofReaderWithDelimiter(unmungedReader, UNMUNGED_FORMAT_DELIMITER);
    }
}

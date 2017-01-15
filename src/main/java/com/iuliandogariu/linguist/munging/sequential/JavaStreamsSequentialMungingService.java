package com.iuliandogariu.linguist.munging.sequential;

import com.iuliandogariu.linguist.munging.PhraseBook;
import com.iuliandogariu.linguist.munging.PhrasePair;
import org.springframework.stereotype.Service;

import java.io.Reader;

/**
 * Returns munged representations of phrase pairs passed in
 * unmunged format.
 *
 * This service works on text streams as java.io.Reader instances.
 *
 * The implementation uses Java 8 Streams. It uses a Scanner to
 * scan the Reader for tokens and place them in a Stream.
 * The process is sequential by nature.
 */
@Service
public class JavaStreamsSequentialMungingService implements SequentialMungingService {

    /**
     * @param unmungedTextReader the unmunged form of the phrase pairs, as a Reader
     * @return munged representation of all the phrase pairs in the text.
     *
     * TODO don't accumulate the results in memory, instead write them out to
     * a Writer using a custom Consumer.
     */
    @Override
    public String mungedPhrasePairsFromStream(Reader unmungedTextReader) {
        return SequentialPhrasePairStream.fromUnmunged(unmungedTextReader)
                .map(PhrasePair::toMungedString)
                .collect(PhraseBook.mungedCollector());
    }

}

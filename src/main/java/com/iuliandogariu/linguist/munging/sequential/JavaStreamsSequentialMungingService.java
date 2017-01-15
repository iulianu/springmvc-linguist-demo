package com.iuliandogariu.linguist.munging.sequential;

import com.iuliandogariu.linguist.munging.PhraseBook;
import com.iuliandogariu.linguist.munging.PhrasePair;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.Writer;

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
     * @param out writer where to write munged phrase pairs.
     */
    @Override
    public void mungedPhrasePairsFromStream(Reader unmungedTextReader, Writer out) {
        SequentialPhrasePairStream.fromUnmunged(unmungedTextReader)
            .map(PhrasePair::toMungedString)
            .collect(
                    () -> new PhrasePairWriterConsumer(out),
                    PhrasePairWriterConsumer::accept,
                    PhrasePairWriterConsumer::combine
            );
    }

}

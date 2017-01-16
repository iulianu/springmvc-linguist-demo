package com.iuliandogariu.linguist.munging.sequential;

import java.io.Reader;
import java.io.Writer;

/**
 * Returns munged representations of phrase pairs passed in
 * unmunged format.
 *
 * This service works on text streams as java.io.Reader instances.
 */
public interface SequentialMungingService {

    /**
     * Returns munged representations of phrase pairs passed in
     * unmunged format.
     *
     * The call to this service method blocks until the input Reader reaches EOF.
     *
     * @param unmungedTextReader the unmunged form of the phrase pairs, as a Reader
     * @param out writer where to write munged phrase pairs.
     */
    void mungedPhrasePairsFromStream(Reader unmungedTextReader, Writer out);

}

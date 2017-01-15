package com.iuliandogariu.linguist.munging.sequential;

import java.io.Reader;

/**
 * Returns munged representations of phrase pairs passed in
 * unmunged format.
 *
 * This service works on text streams as java.io.Reader instances.
 */
public interface SequentialMungingService {

    /**
     * @param unmungedTextReader the unmunged form of the phrase pairs, as a Reader
     * @return munged representation of all the phrase pairs in the text.
     */
    String mungedPhrasePairsFromStream(Reader unmungedTextReader);

}

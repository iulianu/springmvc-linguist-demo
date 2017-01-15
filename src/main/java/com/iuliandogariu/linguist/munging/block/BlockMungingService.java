package com.iuliandogariu.linguist.munging.block;

/**
 * Returns munged representations of phrase pairs passed in
 * unmunged format.
 *
 * This service works on text in memory, represented as String.
 */
public interface BlockMungingService {

    /**
     * @param unmungedText the unmunged form of the phrase pairs
     * @return munged representation of all the phrase pairs in the text.
     */
    String mungedPhrasePairs(String unmungedText);

}

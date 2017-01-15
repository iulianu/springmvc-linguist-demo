package com.iuliandogariu.linguist.ngram;

import java.util.Collection;

/**
 * Accepts tokens in sequence and emits N-grams.
 */
public interface TokenMemory {

    Collection<Ngram> acceptToken(String currentToken);

}

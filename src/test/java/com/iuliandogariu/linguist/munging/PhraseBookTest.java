package com.iuliandogariu.linguist.munging;

import org.junit.Test;
import static org.junit.Assert.*;

public final class PhraseBookTest {

    @Test
    public void shouldConcatenatePhrasePairsIntoMungedRepresentation() {
        PhrasePair pp1 = new PhrasePair(2, "l'homme", 2, "the man", "blah");
        PhrasePair pp2 = new PhrasePair(2, "the woman", 2, "la femme", "lorem");

        PhraseBook phraseBook = new PhraseBook();
        phraseBook.add(pp1);
        phraseBook.add(pp2);

        assertEquals("2 l'homme $ 2 the man & blah | 2 the woman $ 2 la femme & lorem |",
                phraseBook.toMungedString());
    }
}

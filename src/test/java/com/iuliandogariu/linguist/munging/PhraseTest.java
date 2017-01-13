package com.iuliandogariu.linguist.munging;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class PhraseTest {

    @Test
    public void shouldConvertToMungedFormat() {
        Phrase phrase = new Phrase(2, "l'homme");
        assertEquals("2 l'homme", phrase.toMungedString());
    }
}

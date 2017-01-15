package com.iuliandogariu.linguist.munging;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public final class PhraseBookTest {

    @Test
    public void shouldConcatenatePhrasePairsIntoMungedRepresentation() {
        String munged1 = "2 l'homme $ 2 the man & blah |";
        String munged2 = "2 the woman $ 2 la femme & lorem |";

        String mungedPhraseBook = Stream.of(munged1, munged2)
                .collect(PhraseBook.mungedCollector());

        assertEquals("2 l'homme $ 2 the man & blah | 2 the woman $ 2 la femme & lorem |",
                mungedPhraseBook);
    }
}

package com.iuliandogariu.linguist.munging;

import org.junit.Test;
import static org.junit.Assert.*;

public final class PhrasePairTest {

    @Test
    public void shouldConvertToMungedFormat() {
        PhrasePair phrasePair = new PhrasePair(2, "l'homme", 2, "the man", "memo to translators");
        assertEquals("2 l'homme $ 2 the man & memo to translators |",
                phrasePair.toMungedString());
    }

    @Test
    public void shouldCreateFromUnmungedFormat() {
        String unmunged = "PP 2 2 l'homme $ the man & blah";
        PhrasePair pair = PhrasePair.fromUnmungedString(unmunged);
        assertEquals(2, pair.getSourcePhrase().getTokenCount());
        assertEquals("l'homme", pair.getSourcePhrase().getText());
        assertEquals(2, pair.getTargetPhrase().getTokenCount());
        assertEquals("the man", pair.getTargetPhrase().getText());
        assertEquals("blah", pair.getMemo());
    }

    @Test(expected = PhrasePairSyntaxException.class)
    public void shouldFailIfSyntaxIncorrect() {
        String unmunged = "PP brokenString";
        PhrasePair.fromUnmungedString(unmunged);
    }
}

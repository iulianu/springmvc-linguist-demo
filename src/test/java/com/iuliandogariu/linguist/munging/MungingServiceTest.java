package com.iuliandogariu.linguist.munging;

import org.junit.Test;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public final class MungingServiceTest {

    @Test
    public void shouldScanTextForPairs() {
        MungingService service = new MungingService();
        String requestText = "PP 2 2 l' homme $ the man & blah PP 2 2 the woman $ la femme & lorem";
        Stream<String> pairsAsText = service.unmungedPhraseStream(new StringReader(requestText));
        List<String> pairsList = pairsAsText.collect(Collectors.toList());
        assertEquals(2, pairsList.size());
        assertEquals("PP 2 2 l' homme $ the man & blah", pairsList.get(0));
        assertEquals("PP 2 2 the woman $ la femme & lorem", pairsList.get(1));
    }

}

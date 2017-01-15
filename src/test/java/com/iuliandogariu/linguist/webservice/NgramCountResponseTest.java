package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.ngram.Ngram;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public final class NgramCountResponseTest {

    @Test
    public void shouldBuildFromCount() {
        Map<Ngram, Long> counts = new HashMap<>();
        counts.put(new Ngram(3, "I love you"), 69L);
        Map.Entry<Ngram, Long> count = counts.entrySet().iterator().next();
        NgramCountResponse resp = NgramCountResponse.fromNgramCount(count);
        assertEquals("I love you", resp.getNgram());
        assertEquals(69, resp.getCount());
    }

}

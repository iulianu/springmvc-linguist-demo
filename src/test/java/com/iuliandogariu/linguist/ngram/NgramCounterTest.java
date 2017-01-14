package com.iuliandogariu.linguist.ngram;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public final class NgramCounterTest {

    @Test
    public void shouldCountNgrams() {
        NgramCounter counter = new NgramCounter();
        counter.accept("a");
        counter.accept("b");
        counter.accept("a");
        counter.accept("c");
        Map<Ngram, Integer> counts = counter.getCounts();
        // 9 n-grams possible, but the 1-gram "a" is duplicated
        assertEquals(8, counts.size());
        assertEquals(2, counts.get(new Ngram(1, "a")).intValue());
        assertEquals(1, counts.get(new Ngram(1, "b")).intValue());
        assertEquals(1, counts.get(new Ngram(1, "c")).intValue());
        assertEquals(1, counts.get(new Ngram(2, "a b")).intValue());
        assertEquals(1, counts.get(new Ngram(2, "b a")).intValue());
        assertEquals(1, counts.get(new Ngram(2, "a c")).intValue());
        assertEquals(1, counts.get(new Ngram(3, "a b a")).intValue());
        assertEquals(1, counts.get(new Ngram(3, "b a c")).intValue());
    }
}

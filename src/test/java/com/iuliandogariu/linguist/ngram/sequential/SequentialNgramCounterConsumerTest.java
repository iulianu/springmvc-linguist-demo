package com.iuliandogariu.linguist.ngram.sequential;

import com.iuliandogariu.linguist.ngram.Ngram;
import com.iuliandogariu.linguist.ngram.TokenMemory;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public final class SequentialNgramCounterConsumerTest {

    @Test
    public void shouldCountNgrams() {
        TokenMemory tokenMemory = Mockito.mock(TokenMemory.class);
        when(tokenMemory.acceptToken(anyString())).thenReturn(
                Arrays.asList(
                        new Ngram(1, "b"),
                        new Ngram(1, "b"),
                        new Ngram(2, "a b")
                )
        );
        SequentialNgramCounterConsumer counter = new SequentialNgramCounterConsumer(tokenMemory);
        counter.accept("_");
        counter.accept("_");
        Map<Ngram, Long> counts = counter.getCounts();
        // 9 n-grams possible, but the 1-gram "a" is duplicated
        assertEquals(2, counts.size());
        assertEquals(4, counts.get(new Ngram(1, "b")).intValue());
        assertEquals(2, counts.get(new Ngram(2, "a b")).intValue());
    }
}

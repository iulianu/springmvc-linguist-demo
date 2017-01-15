package com.iuliandogariu.linguist.ngram;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class CircularBufferTokenMemoryTest {

    @Test
    public void shouldEmitNgrams() {
        CircularBufferTokenMemory tokenMemory = new CircularBufferTokenMemory(3);
        Collection<Ngram> emittedNgrams;

        emittedNgrams = tokenMemory.acceptToken("a");
        assertThat(emittedNgrams, containsInAnyOrder(new Ngram(1, "a")));

        emittedNgrams = tokenMemory.acceptToken("b");
        assertThat(emittedNgrams, containsInAnyOrder(
                new Ngram(1, "b"),
                new Ngram(2, "a b")
        ));

        emittedNgrams = tokenMemory.acceptToken("a");
        assertThat(emittedNgrams, containsInAnyOrder(
                new Ngram(1, "a"),
                new Ngram(2, "b a"),
                new Ngram(3, "a b a")
        ));

        emittedNgrams = tokenMemory.acceptToken("c");
        assertThat(emittedNgrams, containsInAnyOrder(
                new Ngram(1, "c"),
                new Ngram(2, "a c"),
                new Ngram(3, "b a c")
        ));
    }
}

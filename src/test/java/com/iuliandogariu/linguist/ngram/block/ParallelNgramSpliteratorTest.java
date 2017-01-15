package com.iuliandogariu.linguist.ngram.block;

import com.iuliandogariu.linguist.ngram.Ngram;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public final class ParallelNgramSpliteratorTest {

    @Test
    public void shouldStreamNgramsInParallel() {
        String requestText = "a b c d e f g h i j";
        List<String> ngrams = StreamSupport.stream(
                    new ParallelNgramSpliterator(requestText, 4),true)
                .map(Ngram::getTokens)
                .collect(Collectors.toList());
        assertThat(ngrams, containsInAnyOrder(
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "a b", "b c", "c d", "d e", "e f", "f g", "g h", "h i", "i j",
                "a b c", "b c d", "c d e", "d e f", "e f g", "f g h", "g h i", "h i j"
        ));
    }
}

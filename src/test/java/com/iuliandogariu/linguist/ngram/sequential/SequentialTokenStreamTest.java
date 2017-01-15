package com.iuliandogariu.linguist.ngram.sequential;

import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public final class SequentialTokenStreamTest {

    @Test
    public void shouldTokenizeTextStream() {
        String requestText = "What's in a token ? I bet you cannot tell!";
        Stream<String> tokenStream = SequentialTokenStream.ofReader(new StringReader(requestText));
        List<String> tokensList = tokenStream.collect(Collectors.toList());
        assertEquals(
                Arrays.asList("What", "s", "in", "a", "token", "I", "bet", "you", "cannot", "tell"),
                tokensList);
    }
}

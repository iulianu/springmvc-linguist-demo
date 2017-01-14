package com.iuliandogariu.linguist.ngram;

import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public final class NgramCountServiceTest {

    @Test
    public void shouldTokenizeTextStream() {
        NgramCountService service = new NgramCountService();
        String requestText = "What's in a token ? I bet you cannot tell!";
        Stream<String> tokenStream = service.tokens(new StringReader(requestText));
        List<String> tokensList = tokenStream.collect(Collectors.toList());
        assertEquals(
                Arrays.asList("What", "s", "in", "a", "token", "I", "bet", "you", "cannot", "tell"),
                tokensList);
    }
}

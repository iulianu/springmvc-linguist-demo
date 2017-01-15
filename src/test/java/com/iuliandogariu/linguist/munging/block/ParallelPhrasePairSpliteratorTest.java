package com.iuliandogariu.linguist.munging.block;

import com.iuliandogariu.linguist.munging.PhrasePair;
import com.iuliandogariu.linguist.munging.block.ParallelPhrasePairSpliterator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;


public final class ParallelPhrasePairSpliteratorTest {

    @Test
    public void shouldStreamPhrasePairsInParallel() {
        String requestText = "PP 2 2 l'homme $ the man & blah PP 2 2 the woman $ la femme & lorem PP 1 1 Prost $ cheers & noroc";
        List<PhrasePair> phrasePairs = StreamSupport.stream(
                    new ParallelPhrasePairSpliterator(requestText, 5),true)
                .collect(Collectors.toList());
        assertEquals(
                Arrays.asList(
                        new PhrasePair(2, "l'homme", 2, "the man", "blah"),
                        new PhrasePair(2, "the woman", 2, "la femme", "lorem"),
                        new PhrasePair(1, "Prost", 1, "cheers", "noroc")
                ),
                phrasePairs
        );
    }
}

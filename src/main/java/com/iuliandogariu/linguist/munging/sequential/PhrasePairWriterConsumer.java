package com.iuliandogariu.linguist.munging.sequential;

import com.iuliandogariu.linguist.munging.PhraseBook;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.function.Consumer;

public class PhrasePairWriterConsumer implements Consumer<String> {

    private Writer out;

    public PhrasePairWriterConsumer(Writer out) {
        this.out = out;
    }

    @Override
    public void accept(String phrasePair) {
        try {
            out.write(phrasePair);
            out.write(PhraseBook.OUTPUT_DELIMITER);
        } catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * This collector works only on sequential streams.
     */
    public void combine(PhrasePairWriterConsumer other) {
        throw new AssertionError("No combiners allowed on PhrasePairWriterConsumer");
    }
}

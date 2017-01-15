package com.iuliandogariu.linguist.ngram;

import com.iuliandogariu.linguist.SequentialTokenizingStream;

import java.io.Reader;
import java.util.stream.Stream;

public class SequentialTokenStream {

    /**
     * Returns a stream of tokens from the given Reader, for the
     * purpose of counting Ngrams.
     *
     * Note the returned stream is _not_ parallelizable.
     */
    public static Stream<String> ofReader(Reader textReader) {
        return SequentialTokenizingStream.ofReaderWithDelimiter(textReader, Ngram.DELIMITER_PATTERN);
    }

}

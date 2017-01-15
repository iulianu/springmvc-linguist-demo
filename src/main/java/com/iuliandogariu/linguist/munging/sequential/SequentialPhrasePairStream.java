package com.iuliandogariu.linguist.munging.sequential;

import com.iuliandogariu.linguist.munging.PhrasePair;
import com.iuliandogariu.util.stream.SequentialTokenizingStream;

import java.io.Reader;
import java.util.stream.Stream;

import static com.iuliandogariu.linguist.munging.PhrasePair.UNMUNGED_FORMAT_DELIMITER;

public class SequentialPhrasePairStream {

    /**
     * Scans the text for the delimiter of two concatenated phrase pairs
     * in unmunged form.
     *
     * @param unmungedReader a Reader that gives access to the unmunged text form
     *                       of the phrase pairs
     * @return a Stream of Strings each representing a single phrase pair.
     * Note the returned stream is _not_ parallelizable.
     */
    public static Stream<PhrasePair> fromUnmunged(Reader unmungedReader) {
        return SequentialTokenizingStream.ofReaderWithDelimiter(unmungedReader, UNMUNGED_FORMAT_DELIMITER)
                .map(PhrasePair::fromUnmungedString);
    }
}

package com.iuliandogariu.linguist.munging;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This service exposes two interfaces that do the same thing,
 * but one of them operates in a streaming fashion.
 */
@Service
public class MungingService {

    /**
     * Call this service method if you have a text with the full request text.
     *
     * @param unmungedText the unmunged form of the phrase pairs
     * @return a representation of all the phrase pairs in the text.
     */
    public PhraseBook unmungedPhraseBook(String unmungedText) {
        PhraseBook phraseBook = new PhraseBook();
        Stream<PhrasePair> phraseStream = phrasePairStream(new StringReader(unmungedText));
        phraseStream.forEach(phraseBook::add);
        return phraseBook;
    }

    /**
     * Call this service method if you have a _stream_ of the request text.
     *
     * @param unmungedReader a Reader that gives access to the unmunged text form
     *                       of the phrase pairs
     * @return a stream of all the phrase pairs in the text.
     */
    public Stream<PhrasePair> phrasePairStream(Reader unmungedReader) {
        return unmungedPhraseStream(unmungedReader).map(PhrasePair::fromUnmungedString);
    }

    /**
     * Scans the text for the delimiter of two concatenated phrase pairs
     * in unmunged form.
     *
     * @param unmungedReader a Reader that gives access to the unmunged text form
     *                       of the phrase pairs
     * @return a Stream of Strings each representing a single phrase pair.
     */
    Stream<String> unmungedPhraseStream(Reader unmungedReader) {
        Pattern pairPattern = Pattern.compile("(?:\\A|\\s+)(?=PP \\d+ \\d+)");
        Scanner scanner = new Scanner(unmungedReader);
        scanner.useDelimiter(pairPattern);
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(scanner, Spliterator.ORDERED),
                false);
    }
}

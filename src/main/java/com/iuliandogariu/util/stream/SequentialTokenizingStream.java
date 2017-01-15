package com.iuliandogariu.util.stream;

import java.io.Reader;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SequentialTokenizingStream {

    /**
     * Returns a stream of tokens from the given Reader, using the given
     * pattern as delimiter.
     *
     * Note the returned stream is _not_ parallelizable.
     */
    public static Stream<String> ofReaderWithDelimiter(Reader textReader, Pattern delimiterPattern) {
        Scanner scanner = new Scanner(textReader);
        scanner.useDelimiter(delimiterPattern);
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(scanner, Spliterator.ORDERED),
                false);

    }

}

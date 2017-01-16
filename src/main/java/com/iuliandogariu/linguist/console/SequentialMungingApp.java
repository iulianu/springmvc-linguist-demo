package com.iuliandogariu.linguist.console;

import com.iuliandogariu.linguist.munging.sequential.JavaStreamsSequentialMungingService;
import com.iuliandogariu.linguist.munging.sequential.SequentialMungingService;

import java.io.*;

/**
 * A demo of the sequential munging service on streams.
 * Create a large file with phrase pairs in unmunged format, e.g. in.txt
 * Run it:
 *   java  -cp target/classes com.iuliandogariu.linguist.console.SequentialMungingApp < in.txt > out.txt
 */
public class SequentialMungingApp {
    public static void main(String[] args) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(System.in));
        Writer out = new BufferedWriter(new OutputStreamWriter(System.out));
        SequentialMungingService mungingService = new JavaStreamsSequentialMungingService();
        mungingService.mungedPhrasePairsFromStream(reader, out);
        // The call above BLOCKS until the input stream reaches EOF.
    }
}

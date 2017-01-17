package com.iuliandogariu.linguist.console;

import com.iuliandogariu.linguist.munging.block.BlockMungingService;
import com.iuliandogariu.linguist.munging.block.JavaStreamsBlockMungingService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A demo of the parallel munging service.
 * Create a large file with phrase pairs in unmunged format, e.g. in.txt
 * Run it:
 *   java -Xmx6G -cp target/classes:target/springmvc-linguist-0.1.0/WEB-INF/lib/* \
 *      com.iuliandogariu.linguist.console.ParallelMungingApp in.txt out.txt
 * To observe speedups from parallelism, make sure the input file is at least
 * 80 MB, i.e. twice the size of the maximum split (40MB).
 */
public class ParallelMungingApp {
    public static void main(String[] args) throws IOException {
        System.err.println(System.currentTimeMillis() + ": Starting program");
        String inPath = args[0]; // Let the exceptions bubble up.
        String outPath = args[1];
        String inText = new String(Files.readAllBytes(Paths.get(inPath))); // Ugly, don't do this at home.
        System.err.println(System.currentTimeMillis() + ": File read into memory");
        BlockMungingService mungingService = new JavaStreamsBlockMungingService();
        String out = mungingService.mungedPhrasePairs(inText);
        System.err.println(System.currentTimeMillis() + ": Munging finished");
        FileWriter writer = new FileWriter(outPath, false);
        writer.write(out);
        writer.close();
        System.err.println(System.currentTimeMillis() + ": Output wrote to disk");
    }
}

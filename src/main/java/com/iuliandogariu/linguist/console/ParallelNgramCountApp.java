package com.iuliandogariu.linguist.console;

import com.iuliandogariu.linguist.ngram.Ngram;
import com.iuliandogariu.linguist.ngram.block.BlockNgramCountService;
import com.iuliandogariu.linguist.ngram.block.JavaStreamsBlockNgramCountService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * A demo of the parallel Ngram count service.
 * Create a large file with English text, e.g. in.txt
 * Run it:
 *   java -Xmx6G -cp target/classes:target/springmvc-linguist-0.1.0/WEB-INF/lib/* \
 *     com.iuliandogariu.linguist.console.ParallelNgramCountApp in.txt
 * To observe speedups from parallelism, make sure the input file is at least
 * 80 MB, i.e. twice the size of the maximum split (40MB).
 */
public class ParallelNgramCountApp {
    public static void main(String[] args) throws IOException {
        System.err.println(System.currentTimeMillis() + ": Starting program");
        String inPath = args[0]; // Let the exceptions bubble up.
        String inText = new String(Files.readAllBytes(Paths.get(inPath))); // Ugly, don't do this at home.
        System.err.println(System.currentTimeMillis() + ": File read into memory");
        BlockNgramCountService ngramCountService = new JavaStreamsBlockNgramCountService(40_000_000);
        List<Map.Entry<Ngram, Long>> topCounts = ngramCountService.countNgrams(10, inText);
        System.err.println(System.currentTimeMillis() + ": Ngram count finished");
        for(Map.Entry<Ngram, Long> row : topCounts) {
            System.out.printf("%s x %d\n", row.getKey(), row.getValue());
        }
    }
}

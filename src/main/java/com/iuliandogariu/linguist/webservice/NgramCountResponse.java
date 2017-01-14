package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.ngram.Ngram;

import java.util.Map;

/**
 * Maps an array item in the ngram response.
 *
 */
public final class NgramCountResponse {

    private String ngram;

    private int count;

    public NgramCountResponse(String ngram, int count) {
        this.ngram = ngram;
        this.count = count;
    }

    public String getNgram() {
        return ngram;
    }

    public int getCount() { return count; }

    public static NgramCountResponse fromNgramCount(Map.Entry<Ngram, Integer> ngramCount) {
        // NgramCountResponse and NgramCount have similar fields,
        // but still are distinct classes because they represent distinct
        // concepts.
        return new NgramCountResponse(ngramCount.getKey().getTokens(), ngramCount.getValue());
    }
}

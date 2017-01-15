package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.ngram.Ngram;

import java.util.Map;

/**
 * Maps an array item in the ngram response.
 *
 */
public final class NgramCountResponse {

    private String ngram;

    private long count;

    public NgramCountResponse(String ngram, long count) {
        this.ngram = ngram;
        this.count = count;
    }

    public String getNgram() {
        return ngram;
    }

    public long getCount() { return count; }

    public static NgramCountResponse fromNgramCount(Map.Entry<Ngram, Long> ngramCount) {
        // NgramCountResponse and NgramCount have similar fields,
        // but still are distinct classes because they represent distinct
        // concepts.
        return new NgramCountResponse(ngramCount.getKey().getTokens(), ngramCount.getValue());
    }
}

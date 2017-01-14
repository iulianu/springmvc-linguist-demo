package com.iuliandogariu.linguist.webservice;

import javax.validation.constraints.*;

/**
 * Maps a request to the ngram count endpoint.
 *
 * Validation annotations are for well-formedness only.
 */
public final class NgramCountRequest {

    @Min(1)
    @Max(100)
    private int maxNGramCount;

    @NotNull
    @Size(max = 1048576)
    private String text;

    /**
     * Jackson needs this constructor in order
     * to bind an incoming JSON request to an instance
     * of NgramCountRequest.
     */
    private NgramCountRequest() {}

    public NgramCountRequest(int maxNGramCount, String text) {
        this.maxNGramCount = maxNGramCount;
        this.text = text;
    }

    public int getMaxNGramCount() {
        return maxNGramCount;
    }

    public String getText() {
        return text;
    }

}

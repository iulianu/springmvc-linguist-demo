package com.iuliandogariu.linguist.webservice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Maps a request to the munging endpoint.
 *
 * Validation annotations are for well-formedness only.
 * Extraction of the phrase pairs happens in MungingService.
 */
public final class MungingRequest {

    @NotNull
    @Size(max = 1048576)
    @Pattern(regexp = "PP .*")
    private String text;

    /**
     * Jackson needs this constructor in order
     * to bind an incoming JSON request to an instance
     * of MungingRequest.
     */
    private MungingRequest() {}

    public MungingRequest(String text) {
        this.text = text;
    }

    /** @return original un-munged text */
    public String getText() {
        return text;
    }

}

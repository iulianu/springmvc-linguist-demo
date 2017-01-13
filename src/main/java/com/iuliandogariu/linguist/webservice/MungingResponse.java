package com.iuliandogariu.linguist.webservice;

/**
 * Maps a response that the munging endpoint returns.
 */
public final class MungingResponse {

    private String text;

    public MungingResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}

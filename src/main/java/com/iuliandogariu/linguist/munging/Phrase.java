package com.iuliandogariu.linguist.munging;

/**
 * This class trusts the user to provide the accurate
 * token count. It will not perform any tokenization of its own.
 */
public final class Phrase {
    private int tokenCount;
    private String text;

    public Phrase(int tokenCount, String text) {
        this.tokenCount = tokenCount;
        this.text = text;
    }

    public int getTokenCount() { return tokenCount; }

    public String getText() { return text; }

    public String toMungedString() {
        return tokenCount + " " + text;
    }
}

package com.iuliandogariu.linguist.munging;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public String toString() {
        return "{" + tokenCount + "," + text + "}";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 37).
                append(tokenCount).
                append(text).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        Phrase rhs = (Phrase) obj;
        return new EqualsBuilder()
                .append(tokenCount, rhs.tokenCount)
                .append(text, rhs.text)
                .isEquals();
    }

}

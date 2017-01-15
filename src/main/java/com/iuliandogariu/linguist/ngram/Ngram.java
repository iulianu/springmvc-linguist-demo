package com.iuliandogariu.linguist.ngram;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.regex.Pattern;

public final class Ngram {

    public static final Pattern DELIMITER_PATTERN = Pattern.compile("\\W+");

    private int order;
    private String tokens;

    public Ngram(int order, String tokens) {
        this.order = order;
        this.tokens = tokens;
    }

    public int getOrder() { return order; }

    public String getTokens() { return tokens; }

    @Override
    public String toString() {
        return "{" + order + "," + tokens + "}";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(5, 37).
                append(order).
                append(tokens).
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

        Ngram rhs = (Ngram) obj;
        return new EqualsBuilder()
                .append(order, rhs.order)
                .append(tokens, rhs.tokens)
                .isEquals();
    }
}

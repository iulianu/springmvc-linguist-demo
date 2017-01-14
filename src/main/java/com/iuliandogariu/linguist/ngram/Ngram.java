package com.iuliandogariu.linguist.ngram;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Ngram implements Comparable<Ngram> {

    private int order;
    private String tokens;

    public Ngram(int order, String tokens) {
        this.order = order;
        this.tokens = tokens;
    }

    public int getOrder() { return order; }

    public String getTokens() { return tokens; }

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

    @Override
    public int compareTo(Ngram o) {
         return new CompareToBuilder()
            .append(this.order, o.order)
            .append(this.tokens, o.tokens)
            .toComparison();
    }
}

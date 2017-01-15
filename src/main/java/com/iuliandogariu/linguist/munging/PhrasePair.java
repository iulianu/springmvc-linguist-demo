package com.iuliandogariu.linguist.munging;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PhrasePair {

    public static final Pattern UNMUNGED_FORMAT_DELIMITER =
            Pattern.compile("(?:\\A|\\s+)(?=\\Z|PP \\d+ \\d+)");

    private Phrase sourcePhrase;
    private Phrase targetPhrase;
    private String memo;

    public PhrasePair(
            int sourceTokenCount,
            String sourceText,
            int targetTokenCount,
            String targetText,
            String memo) {
        this.sourcePhrase = new Phrase(sourceTokenCount, sourceText);
        this.targetPhrase = new Phrase(targetTokenCount, targetText);
        this.memo = memo;
    }

    public Phrase getSourcePhrase() { return sourcePhrase; }

    public Phrase getTargetPhrase() { return targetPhrase; }

    public String getMemo() { return memo; }

    public final String toMungedString() {
        return sourcePhrase.toMungedString()
                + " $ "
                + targetPhrase.toMungedString()
                + " & "
                + memo
                + " |";
    }

    public static PhrasePair fromUnmungedString(String str) {
        Pattern phrasesPattern = Pattern.compile("PP (\\d+) (\\d+) (.*) \\$ (.*) & (.*)");
        Matcher mat = phrasesPattern.matcher(str);
        if(!mat.matches()) {
            throw new PhrasePairSyntaxException();
        }
        try  {
            int sourceTokenCount = Integer.parseInt(mat.group(1));
            String sourceText = mat.group(3);
            int targetTokenCount = Integer.parseInt(mat.group(2));
            String targetText = mat.group(4);
            String memo = mat.group(5);
            return new PhrasePair(sourceTokenCount, sourceText, targetTokenCount, targetText, memo);
        } catch(NumberFormatException e) {
            throw new PhrasePairSyntaxException();
        }
    }

    @Override
    public String toString() {
        return "{" + sourcePhrase + ";" + targetPhrase + ";" + memo + "}";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 37).
                append(sourcePhrase).
                append(targetPhrase).
                append(memo).
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

        PhrasePair rhs = (PhrasePair) obj;
        return new EqualsBuilder()
                .append(sourcePhrase, rhs.sourcePhrase)
                .append(targetPhrase, rhs.targetPhrase)
                .append(memo, rhs.memo)
                .isEquals();
    }

}

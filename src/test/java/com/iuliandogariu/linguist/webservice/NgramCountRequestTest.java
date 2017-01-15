package com.iuliandogariu.linguist.webservice;

import org.junit.Test;

import static com.iuliandogariu.test.JavaxValidationAssertions.*;

public final class NgramCountRequestTest {

    @Test
    public void shouldAllowWellFormedRequest() {
        NgramCountRequest req = new NgramCountRequest(6, "Hello says the devil");
        assertNoViolation(req);
    }

    @Test
    public void shouldNotAllowMaxCountOutsidOfRange() {
        NgramCountRequest req = new NgramCountRequest(444, "Hello says the devil");
        assertOneViolation(req, "maxNGramCount", "must be less than or equal to 100");
    }

    @Test
    public void shouldNotAllowNulls() {
        NgramCountRequest req = new NgramCountRequest(4, null);
        assertOneViolation(req, "text", "may not be null");
    }

    @Test
    public void shouldNotAllowBigStrings() {
        // Approximately 1 MB of English text encoded in UTF-8 or ASCII
        char[] bigBuffer = new char[1048577];
        NgramCountRequest req = new NgramCountRequest(4, new String(bigBuffer));
        assertOneViolation(req, "text", "size must be between 0 and 1048576");
    }
}

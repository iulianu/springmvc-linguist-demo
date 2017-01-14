package com.iuliandogariu.linguist.webservice;

import org.junit.Test;

import static com.iuliandogariu.linguist.webservice.JavaxValidationAssertions.*;

public final class MungingRequestTest {

    @Test
    public void shouldAllowWellFormedRequest() {
        MungingRequest req = new MungingRequest("PP 2 2 l'homme $ the man & blah");
        assertNoViolation(req);
    }

    @Test
    public void shouldNotAllowNulls() {
        MungingRequest req = new MungingRequest(null);
        assertOneViolation(req, "text", "may not be null");
    }

    @Test
    public void shouldAllowOnlyPhrasePairs() {
        MungingRequest req = new MungingRequest("This Is Not A Valid Request");
        assertOneViolation(req, "text", "must match \"PP .*\"");
    }

    @Test
    public void shouldNotAllowBigStrings() {
        // Approximately 1 MB of English text encoded in UTF-8 or ASCII
        char[] bigBuffer = new char[1048577];
        String wellFormedBigText = "PP 2 2 l'homme $ the man & blah" + new String(bigBuffer);
        MungingRequest req = new MungingRequest(wellFormedBigText);
        assertOneViolation(req, "text", "size must be between 0 and 1048576");
    }
}

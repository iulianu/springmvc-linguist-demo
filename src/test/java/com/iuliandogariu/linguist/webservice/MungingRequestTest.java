package com.iuliandogariu.linguist.webservice;

import com.iuliandogariu.linguist.webservice.MungingRequest;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    private Validator validator;

    public MungingRequestTest() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    private void assertNoViolation(MungingRequest req) {
        Set<ConstraintViolation<MungingRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
    }

    private void assertOneViolation(MungingRequest req, String field, String message) {
        Set<ConstraintViolation<MungingRequest>> violations = validator.validate(req);
        assertEquals(1, violations.size());
        ConstraintViolation<MungingRequest> violation = violations.iterator().next();
        assertEquals(field, violation.getPropertyPath().toString());
        assertEquals(message, violation.getMessage());
    }
}

package com.iuliandogariu.linguist.webservice;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaxValidationAssertions {

    public static <T> void assertNoViolation(T object) {
        Validator validator = buildValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        assertTrue(violations.isEmpty());
    }

    public static <T> void assertOneViolation(T object, String field, String message) {
        Validator validator = buildValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        assertEquals(1, violations.size());
        ConstraintViolation<T> violation = violations.iterator().next();
        assertEquals(field, violation.getPropertyPath().toString());
        assertEquals(message, violation.getMessage());
    }

    private static Validator buildValidator() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }
}

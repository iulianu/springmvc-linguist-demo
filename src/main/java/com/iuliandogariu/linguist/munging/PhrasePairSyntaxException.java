package com.iuliandogariu.linguist.munging;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Munging Service throws this exception when it fails to parse
 * the phrase pair from the unmunged text representation.
 *
 * TODO add detail and cause.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The phrase pair syntax was incorrect")
public class PhrasePairSyntaxException extends RuntimeException {
}

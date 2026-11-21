package com.universityconnect.shared.error;

import org.springframework.http.HttpStatus;

/** Request is well-formed but violates a business rule (HTTP 422). */
public class BusinessRuleException extends ApiException {
    public BusinessRuleException(String code, String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, code, message);
    }
}

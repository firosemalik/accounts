package com.anz.accounts.controller.validator;

import com.anz.accounts.api.MandatoryHeaders;
import com.anz.accounts.controller.exception.AuthorisationException;
import com.anz.accounts.controller.exception.InvalidRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RequestHeaderValidator implements ConstraintValidator<RequiredHeaders, HttpHeaders> {

    @Override
    public void initialize(RequiredHeaders constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(HttpHeaders requestHeaders, ConstraintValidatorContext context) {
        if (requestHeaders == null) {
            throw new IllegalArgumentException("HttpHeaders is null");
        }
        if (Strings.isBlank(requestHeaders.getFirst(MandatoryHeaders.TRACE_ID))) {
            throw new InvalidRequestException("Request header TRACE_ID missing");
        }
        if (Strings.isBlank(requestHeaders.getFirst(MandatoryHeaders.ACCESS_TOKEN))) {
            throw new AuthorisationException("Request header ACCESS_TOKEN missing");
        }
        return true;
    }
}

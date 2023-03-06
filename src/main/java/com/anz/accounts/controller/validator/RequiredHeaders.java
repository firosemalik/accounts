package com.anz.accounts.controller.validator;

import org.springframework.core.annotation.Order;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Order(2)
@Constraint(
        validatedBy = {RequestHeaderValidator.class}
)
public @interface RequiredHeaders {
    String message() default "Not a valid header";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package com.example.javalab.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {MaxDoubleValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface MaxDouble {
    String message() default "Value exceeds maximum allowed value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double value();
}

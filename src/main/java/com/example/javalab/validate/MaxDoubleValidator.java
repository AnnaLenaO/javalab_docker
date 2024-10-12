package com.example.javalab.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxDoubleValidator implements ConstraintValidator<MaxDouble, Double> {
    @Override
    public boolean isValid(Double input, ConstraintValidatorContext constraintValidatorContext) {

        return input <= 10.0;
    }
}

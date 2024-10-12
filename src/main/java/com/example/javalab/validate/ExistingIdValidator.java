package com.example.javalab.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ExistingIdValidator implements ConstraintValidator<ExistingId, String> {

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {

        Pattern UUID_REGEX = Pattern.compile(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
        );

        return input != null && !input.isEmpty() && UUID_REGEX.matcher(input).matches();
    }
}

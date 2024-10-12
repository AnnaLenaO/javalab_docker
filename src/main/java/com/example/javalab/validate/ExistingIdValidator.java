package com.example.javalab.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class ExistingIdValidator implements ConstraintValidator<ExistingId, String> {
    private static final Logger logger = LoggerFactory.getLogger(ExistingCategoryValidator.class);

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        logger.info("Validating id: {}", input);

        Pattern UUID_REGEX = Pattern.compile(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
        );
//        return input != null && !input.isEmpty() && UUID_REGEX.matcher(input).matches();
        boolean isValid = UUID_REGEX.matcher(input).matches();

        if (!isValid) {
            logger.warn("Invalid ID: {}", input);
        } else {
            logger.info("Valid ID: {}", input);
        }
        return isValid;
    }
}

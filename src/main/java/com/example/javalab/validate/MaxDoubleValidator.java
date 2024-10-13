package com.example.javalab.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaxDoubleValidator implements ConstraintValidator<MaxDouble, Double> {
    private static final Logger logger = LoggerFactory.getLogger(MaxDoubleValidator.class);

    @Override
    public boolean isValid(Double input, ConstraintValidatorContext constraintValidatorContext) {
        logger.info("Validating rating: {}", input);

        if (input == null) {
            logger.info("Null rating. No MaxDouble validation passed.");
            return true;
        }

        boolean isValid = input <= 10.00;

        if (!isValid) {
            logger.warn("Invalid rating: {}", input);
        } else {
            logger.info("Valid rating: {}", input);
        }
        return isValid;
    }
}

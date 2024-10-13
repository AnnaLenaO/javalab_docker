package com.example.javalab.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.javalab.entities.Category.RUGOSA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InputProductDataTest {

    private final Validator validator;

    public InputProductDataTest() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    @Test
    void inputProductDataWithEmptyName() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData("", RUGOSA, 6.0);

            Set<ConstraintViolation<InputProductData>> violations = validator.validate(inputProductData);
            Set<String> messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());

            assertThat(violations).isNotEmpty();
            assertThat(messages).contains("Empty names not allowed");
        }
    }

    @Test
    void inputProductDataWithOverMaxSize() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData =
                    new InputProductData("Wasagamin and Here it si something wrong with this n", RUGOSA, 6.0);

            Set<ConstraintViolation<InputProductData>> violations = validator.validate(inputProductData);
            Set<String> messages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());

            assertThat(violations).isNotEmpty();
            assertThat(messages).contains("Name can not contain more than 50 characters");
        }
    }
}

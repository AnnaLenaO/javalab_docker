package com.example.javalab.entities;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.example.javalab.entities.Category.RUGOSA;
import static org.assertj.core.api.Assertions.assertThat;

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

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "name".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation
                    .getMessage().contains("Empty names not allowed"));
        }
    }

    @Test
    void inputProductDataWithNameOverMaxSize() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData(
                    "Wasagaming and Here it is something wrong with this n", RUGOSA, 6.0);

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "name".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation.getMessage()
                    .contains("Name can not contain more than 50 characters"));
        }
    }

    @Test
    void inputProductDataWithNullCategory() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData(
                    "Wasagaming", null, 6.0);

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "category".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation
                    .getMessage().contains("must not be null"));
        }
    }

    @Test
    void inputProductDataWithNoExistingCategory() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData(
                    "Not existing", null, 6.0);

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "category".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation
                    .getMessage().contains("Category does not exist"));
        }
    }

    @Test
    void inputProductDataWithNullRating() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData(
                    "Wasagaming", RUGOSA, null);

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "rating".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation
                    .getMessage().contains("must not be null"));
        }
    }

    @Test
    void inputProductDataWithNoPositiveOrZeroRating() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData(
                    "Wasagaming", RUGOSA, -0.01);

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "rating".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation
                    .getMessage().contains("Rating can not be less than 0.00"));
        }
    }

    @Test
    void inputProductDataWithMoreThanMaxRating() {
        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();

            InputProductData inputProductData = new InputProductData(
                    "Wasagaming", RUGOSA, 10.01);

            Set<ConstraintViolation<InputProductData>> violations =
                    validator.validate(inputProductData);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(violation -> "rating".equals(violation
                    .getPropertyPath().toString()));
            assertThat(violations).anyMatch(violation -> violation
                    .getMessage().contains("Rating can not be more than 10.00"));
        }
    }

    @Test
    void inputProductDataWithValidInput() {
        InputProductData inputProductData = new InputProductData(
                "Wasagaming", RUGOSA, 6.0);

        Set<ConstraintViolation<InputProductData>> violations = validator.validate(inputProductData);

        assertThat(violations).isEmpty();
    }
}

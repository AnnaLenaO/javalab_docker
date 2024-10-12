package com.example.javalab.validate;

import com.example.javalab.entities.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ExistingCategoryValidator implements ConstraintValidator<ExistingCategory, Category> {
    private static final Logger logger = LoggerFactory.getLogger(ExistingCategoryValidator.class);

    private Set<Category> categories;

    @Override
    public void initialize(ExistingCategory constraintAnnotation) {
        this.categories = Collections.synchronizedSet(EnumSet.allOf(Category.class));
        logger.info("Initializing validator with categories: {}", categories);
    }

    @Override
    public boolean isValid(Category input, ConstraintValidatorContext constraintValidatorContext) {
//        return input != null && categories.contains(input);
        boolean isValid = categories.contains(input);

        if (!isValid) {
            logger.warn("Invalid category: {}", input);
        } else {
            logger.info("Valid category: {}", input);
        }
        return isValid;
    }
}

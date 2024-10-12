package com.example.javalab.validate;

import com.example.javalab.entities.Category;
import com.example.javalab.exceptionmapper.IllegalCategoryException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class ExistingCategoryValidator implements ConstraintValidator<ExistingCategory, Category> {
    private Set<Category> categories;

    @Override
    public void initialize(ExistingCategory constraintAnnotation) {
        this.categories = Collections.synchronizedSet(EnumSet.allOf(Category.class));
    }

    @Override
    public boolean isValid(Category input, ConstraintValidatorContext constraintValidatorContext) {
        return input != null && categories.contains(input);
    }
}

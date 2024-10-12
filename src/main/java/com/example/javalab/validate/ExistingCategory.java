package com.example.javalab.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = {ExistingCategoryValidator.class})
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ExistingCategory {

    String message() default "Category does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

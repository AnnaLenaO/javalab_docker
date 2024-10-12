package com.example.javalab.entities;

//import com.example.javalab.deserializer.CategoryDeserializer;
import com.example.javalab.validate.ExistingCategory;
import com.example.javalab.validate.MaxDouble;
//import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.validation.constraints.*;

public record InputProductData(
        @NotEmpty(message = "Empty names not allowed")
        @Size(max = 50, message = "Name can not contain more than 50 characters")
        String name,
        @NotNull
//        @JsonbTypeDeserializer(CategoryDeserializer.class)
        @ExistingCategory
        Category category,
        @NotNull
        @PositiveOrZero(message = "Rating can not be less than 0.00")
        @MaxDouble(value = 10.00, message = "Rating can not be more than 10.00")
        Double rating) {
}

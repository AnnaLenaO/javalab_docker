//package com.example.javalab.deserializer;
//
//import com.example.javalab.entities.Category;
//import com.example.javalab.exceptionmapper.IllegalCategoryException;
//import jakarta.json.bind.JsonbException;
//import jakarta.json.bind.serializer.DeserializationContext;
//import jakarta.json.bind.serializer.JsonbDeserializer;
//import jakarta.json.stream.JsonParser;
//
//import java.lang.reflect.Type;
//
//public class CategoryDeserializer implements JsonbDeserializer<Category> {
//
//    @Override
//    public Category deserialize(JsonParser parser, DeserializationContext deserializationContext, Type type) {
//        String value = parser.getString();
//        try {
//            return Category.valueOf(value);
//        } catch (IllegalArgumentException exception) {
////            throw new IllegalCategoryException("Category does not exist: " + value);
//            throw new JsonbException("Category does not exist: " + value);
////        }
////        return Category.valueOf(value);
//        }
//    }
//}

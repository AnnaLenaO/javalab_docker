package com.example.javalab.exceptionmapper;

public class IllegalCategoryException extends RuntimeException {
    //from example link in lecture
    public IllegalCategoryException() {
        super();
    }

    public IllegalCategoryException(String message) {
        super(message);
    }
}

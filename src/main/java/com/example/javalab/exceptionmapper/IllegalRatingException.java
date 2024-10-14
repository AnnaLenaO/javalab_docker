package com.example.javalab.exceptionmapper;

public class IllegalRatingException extends RuntimeException {

    public IllegalRatingException() {
        super();
    }

    public IllegalRatingException(String message) {
        super(message);
    }
}

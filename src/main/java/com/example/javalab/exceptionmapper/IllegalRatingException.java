package com.example.javalab.exceptionmapper;

import jakarta.ws.rs.WebApplicationException;

public class IllegalRatingException extends WebApplicationException {

    public IllegalRatingException() {
        super();
    }

    public IllegalRatingException(String message) {
        super(message);
    }
}

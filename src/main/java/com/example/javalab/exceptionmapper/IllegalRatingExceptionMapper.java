package com.example.javalab.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalRatingExceptionMapper implements ExceptionMapper<IllegalRatingException> {

    @Override
    public Response toResponse(IllegalRatingException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}

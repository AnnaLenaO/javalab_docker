package com.example.javalab.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalCategoryExceptionMapper implements ExceptionMapper<IllegalCategoryException> {

    @Override
    public Response toResponse(IllegalCategoryException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Category does not exist")
                .build();
    }
}

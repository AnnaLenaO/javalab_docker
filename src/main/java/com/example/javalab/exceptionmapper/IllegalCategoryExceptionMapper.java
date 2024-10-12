package com.example.javalab.exceptionmapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class IllegalCategoryExceptionMapper implements ExceptionMapper<IllegalCategoryException> {
    private static final Logger logger = LoggerFactory.getLogger(IllegalCategoryExceptionMapper.class.getName());

    @Override
    public Response toResponse(IllegalCategoryException exception) {
        logger.error(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Category does not exist")
                .build();
    }
}

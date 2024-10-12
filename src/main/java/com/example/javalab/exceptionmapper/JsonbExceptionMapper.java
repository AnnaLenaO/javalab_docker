package com.example.javalab.exceptionmapper;

import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class JsonbExceptionMapper implements ExceptionMapper<JsonbException> {
    private static final Logger logger = LoggerFactory.getLogger(JsonbExceptionMapper.class.getName());

    @Override
    public Response toResponse(JsonbException exception) {
        logger.error(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }
}

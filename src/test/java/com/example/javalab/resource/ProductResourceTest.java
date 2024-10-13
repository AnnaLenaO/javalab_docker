package com.example.javalab.resource;

import com.example.javalab.entities.InputProductData;
import com.example.javalab.exceptionmapper.*;
import com.example.javalab.service.ImplWarehouse;
import com.example.javalab.service.Warehouse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;

import static com.example.javalab.entities.Category.RUGOSA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductResourceTest {
    Dispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        dispatcher = MockDispatcherFactory.createDispatcher();

        Warehouse service = new ImplWarehouse();
        ProductResource productResource = new ProductResource(service);

        dispatcher.getRegistry().addSingletonResource(productResource);

        ExceptionMapper<IllegalRatingException> exceptionMapperRating = new IllegalRatingExceptionMapper();
        ExceptionMapper<IllegalCategoryException> exceptionMapperCategory = new IllegalCategoryExceptionMapper();
        ExceptionMapper<ConstraintViolationException> exceptionMapperConstraint = new ConstraintViolationExceptionMapper();

        dispatcher.getProviderFactory().registerProviderInstance(exceptionMapperCategory);
        dispatcher.getProviderFactory().registerProviderInstance(exceptionMapperRating);
        dispatcher.getProviderFactory().registerProviderInstance(exceptionMapperConstraint);
    }

    @Test
    void postJsonRepresentInputProductData() throws
            URISyntaxException, JsonProcessingException, UnsupportedEncodingException, JSONException {
        InputProductData inputProductData = new InputProductData("Wasagaming", RUGOSA, 6.0);

        MockHttpRequest request = MockHttpRequest.post("/products");
        String json = new ObjectMapper().writeValueAsString(inputProductData);
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        String responseContent = response.getContentAsString();
        JSONObject expected = new JSONObject(json);
        JSONObject actual = new JSONObject(responseContent);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
        assertEquals(201, response.getStatus());
    }

    @Test
    void postHaveValidAnnotationOnInputProductDataParameter() throws NoSuchMethodException {
        Method method = ProductResource.class.getMethod("addProduct", InputProductData.class, String.class);
        Parameter[] parameters = method.getParameters();
        boolean isAnnotationPresent = parameters[0].isAnnotationPresent(Valid.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    void postHaveHeaderParamAnnotationOnInputProductDataParameter() throws NoSuchMethodException {
        Method method = ProductResource.class.getMethod("addProduct", InputProductData.class, String.class);
        Parameter[] parameters = method.getParameters();
        boolean isAnnotationPresent = parameters[1].isAnnotationPresent(HeaderParam.class);

        assertThat(isAnnotationPresent).isTrue();
    }
}
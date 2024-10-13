package com.example.javalab.resource;

import com.example.javalab.entities.InputProductData;
import com.example.javalab.entities.Product;
import com.example.javalab.exceptionmapper.IllegalCategoryException;
import com.example.javalab.exceptionmapper.IllegalCategoryExceptionMapper;
import com.example.javalab.exceptionmapper.IllegalRatingException;
import com.example.javalab.exceptionmapper.IllegalRatingExceptionMapper;
import com.example.javalab.service.ImplWarehouse;
import com.example.javalab.service.Warehouse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.JsonException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static com.example.javalab.entities.Category.RUGOSA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductResourceTest {
    Dispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        Warehouse service = new ImplWarehouse();
        ProductResource productResource = new ProductResource(service);
        dispatcher.getRegistry().addSingletonResource(productResource);
        dispatcher.getProviderFactory().registerProviderInstance(service);

        ExceptionMapper<IllegalRatingException> exceptionMapperRating = new IllegalRatingExceptionMapper();
        ExceptionMapper<IllegalCategoryException> exceptionMapperCategory = new IllegalCategoryExceptionMapper();

        dispatcher.getProviderFactory().registerProviderInstance(exceptionMapperCategory);
        dispatcher.getProviderFactory().registerProviderInstance(exceptionMapperRating);
    }

    @Test
    void addProductShouldReturnAddedProduct() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.get("/products");
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        //Assert that product returns as Json tex.
//        assertThat(response).returns(Product);
    }

    @Test
//GÖR MER HÄR!!!!
    void whenPostingJsonRepresentPersonShouldGet201Created() throws URISyntaxException, JsonProcessingException {
        InputProductData inputProductData = new InputProductData("Wasagaming", RUGOSA, 6.0);

        MockHttpRequest request = MockHttpRequest.post("/products");
        String json = new ObjectMapper().writeValueAsString(inputProductData);
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        //Add more for request. to construct the test

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        //Assert that product returns as Json tex.
    }
}
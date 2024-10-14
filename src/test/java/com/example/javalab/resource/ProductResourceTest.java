package com.example.javalab.resource;

import com.example.javalab.entities.Category;
import com.example.javalab.entities.InputProductData;
import com.example.javalab.entities.Product;
import com.example.javalab.entities.ProductList;
import com.example.javalab.exceptionmapper.*;
import com.example.javalab.service.ImplWarehouse;
import com.example.javalab.service.Warehouse;
import com.example.javalab.validate.ExistingCategory;
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
import java.time.LocalDate;
import java.util.UUID;

import static com.example.javalab.entities.Category.CANADIAN;
import static com.example.javalab.entities.Category.RUGOSA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductResourceTest {
    Dispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        dispatcher = MockDispatcherFactory.createDispatcher();

        Warehouse service = new ImplWarehouse();

        UUID id = UUID.fromString("d9104250-8222-496f-882b-a4e4c1a016f8");
        String name = "Wasagaming";
        Double rating = 6.0;
        LocalDate createdAt = LocalDate.of(2024, 10, 13);
        LocalDate updatedAt = LocalDate.of(2024, 10, 13);

        Product product = new Product(id, name, RUGOSA, rating, createdAt, updatedAt);
        ProductList.addProduct(product);

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
    void postJsonRepresentInputProductDataReturn201CreatedJson() throws
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

    @Test
    void getJsonRepresentProductList() throws URISyntaxException, UnsupportedEncodingException {
        MockHttpRequest request = MockHttpRequest.get("/products");
        //Does not support & include LocalDate:
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        String responseContent = response.getContentAsString();

        assertThat(responseContent).contains("Wasagaming");
        assertEquals(200, response.getStatus());

//        This works for array:
//        String expectedJson = """
//                [{"product":{}}]""";//
//        JSONArray expected = new JSONArray(expectedJson);

        //This does not work because of LocalDate.
//        JSONArray actual = new JSONArray(responseContent);

        //Alternative with JSONObject:
//        JSONArray actualResponseArray = new JSONArray(responseContent);
//        JSONObject actual = new JSONObject();
//        actual.put("products", actualResponseArray);

//        String expectedJson = """
//                {[{"product":{}}]}""";

        //Alternative:
//        String expectedJson = """
//                {"products": []}""";
//
//        JSONObject expected = new JSONObject(expectedJson);
//        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
    }

    @Test
    void getProductListForProductIdReturn200ok() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/products/" +
                UUID.fromString("d9104250-8222-496f-882b-a4e4c1a016f8"));
        //Generate expected failing test:
//        MockHttpRequest request = MockHttpRequest.get("/products/" +
//                UUID.fromString("d9104250-8222-496f-882b-a4e4c1a016f1"));
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
    }

    @Test
    void getForInvalidProductIdReturn404NotFound() throws URISyntaxException {
        UUID productId = UUID.randomUUID();
        String name = "Wasagaming";
        Double rating = 6.0;
        LocalDate createdAt = LocalDate.of(2024, 10, 13);
        LocalDate updatedAt = LocalDate.of(2024, 10, 13);

        Product product = new Product(productId, name, RUGOSA, rating, createdAt, updatedAt);
        ProductList.addProduct(product);
        UUID invalidProductId = UUID.randomUUID();
//
        MockHttpRequest request = MockHttpRequest.get("/products/" + invalidProductId);
        //Generate expected failing test:
//        MockHttpRequest request = MockHttpRequest.get("/products/" + product.id());
        //Generate false positive test:
//        MockHttpRequest request = MockHttpRequest.get("/products" +
//                UUID.fromString("d9104250-8222-496f-882b-a4e4c1a016f8"));
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(404, response.getStatus());
    }

    @Test
    void getProductListForProductIdHaveValidAnnotationOnUUIDParameter() throws NoSuchMethodException {
        Method method = ProductResource.class.getMethod("getProductById", UUID.class);
        Parameter[] parameters = method.getParameters();
        boolean isAnnotationPresent = parameters[0].isAnnotationPresent(Valid.class);

        assertThat(isAnnotationPresent).isTrue();
    }

    @Test
    void getProductListForProductCategoryReturn200ok() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/products/categories/" + RUGOSA);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
    }

    @Test
    void getForInvalidProductCategoryReturn404NotFound() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/products/categories/" + CANADIAN);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(404, response.getStatus());
    }

    @Test
    void getProductListForProductCategoryHaveValidAnnotationOnCategoryParameter() throws NoSuchMethodException {
        Method method = ProductResource.class.getMethod("getCategoryProducts", Category.class);
        Parameter[] parameters = method.getParameters();
        boolean isAnnotationPresent = parameters[0].isAnnotationPresent(ExistingCategory.class);

        assertThat(isAnnotationPresent).isTrue();
    }
}
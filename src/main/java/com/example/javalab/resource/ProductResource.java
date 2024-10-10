package com.example.javalab.resource;

import com.example.javalab.entities.Category;
import com.example.javalab.entities.InputProductData;
import com.example.javalab.entities.ProductList;
import com.example.javalab.service.Warehouse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.UUID;

@Path("/")
public class ProductResource {
    private Warehouse warehouse;

    @Context
    private UriInfo uriInfo;

    public ProductResource() {
    }

    @Inject
    public ProductResource(Warehouse warehouse) {
        System.out.println("Warehouse created");
        this.warehouse = warehouse;
    }

    @POST
    @Path("/products")
    @Consumes("application/json")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createProduct(InputProductData product) {
        warehouse.addNewProduct(product);
//        return Response.created(uriInfo.getAbsolutePath()).build();
        return Response.created(URI.create("/products")).build();
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductList allProducts() {
        return new ProductList(warehouse.getProductList());
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") UUID id) {
//        warehouse.getAProductForItsId(id);
//        return Response.created(URI.create("/products/" + id)).build();
        return Response.created(URI.create("/products/" + id))
                .entity(warehouse.getAProductForItsId(id)).build();
    }

    @GET
    @Path("/products/categories/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductCategories(@PathParam("category") Category category) {
        return Response.created(URI.create("/categories/" + category))
                .entity(warehouse.getSortedProductsForACategory(category)).build();
    }
}

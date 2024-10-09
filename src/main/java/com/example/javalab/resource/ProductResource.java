package com.example.javalab.resource;

import com.example.javalab.entities.ProductList;
import com.example.javalab.service.Warehouse;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

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

    @GET
    @Path("/products")
    @Produces("application/json")
//    @Produces(MediaType.APPLICATION.JSON)
    public ProductList allProducts() {
        return new ProductList(warehouse.getProductList());
    }
}

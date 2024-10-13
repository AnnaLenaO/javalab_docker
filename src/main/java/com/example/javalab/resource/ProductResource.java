package com.example.javalab.resource;

import com.example.javalab.entities.Category;
import com.example.javalab.entities.InputProductData;
import com.example.javalab.entities.Product;
import com.example.javalab.interceptor.Log;
import com.example.javalab.service.ImplWarehouse;
import com.example.javalab.service.Warehouse;
import com.example.javalab.validate.ExistingCategory;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/")
@Log
public class ProductResource {

    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid InputProductData product,
                               @HeaderParam("X-Forwarded-Proto") String proto) {

        warehouse.addNewProduct(product);

        logger.info("Product created: {}", product);
        logger.info("Product added to Warehouse: {}", product);

        if (proto == null) {
            proto = uriInfo.getRequestUri().getScheme();
        }

        URI baseUri = uriInfo.getBaseUri();
        String fullPath = baseUri.getPath() + "/products";
        URI location = URI.create(proto
                + "://"
                + baseUri.getHost()
                + (baseUri.getPort() != -1 ? ":" + baseUri.getPort() : "")
                + fullPath);

        return Response.status(Response.Status.CREATED).entity(product).location(location).build();
    }

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        List<Product> productList = warehouse.getProductList();
        logger.info("Return {} products in list ", productList.size());

        return Response.ok().entity(productList).build();

//        return Response.created(URI.create("/products"))
//                .entity(warehouse.getProductList()).build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") @Valid UUID id) {

        UUID productId = UUID.fromString(String.valueOf(id));
        Optional<Product> product = warehouse.getAProductForItsId(productId);

        if (product.isEmpty()) {
            logger.warn("No product with id: {}", id);

            return Response.status(Response.Status.NOT_FOUND)
                    .header("Custom-error", "Id does not exist. Try again").build();
        }
        return Response.ok().entity(product.get()).build();
    }

    @GET
    @Path("/products/categories/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductCategories(@PathParam("category") @ExistingCategory Category category) {

        ImplWarehouse.SortedProducts categoryProducts =
                (ImplWarehouse.SortedProducts) warehouse.getSortedProductsForACategory(category);

        if (categoryProducts.product().isEmpty()) {
            logger.warn("No products with category: {}", category);

            return Response.status(Response.Status.NOT_FOUND)
                    .header("Custom-error", "Category has no products. Try again").build();
        }
        return Response.ok().entity(categoryProducts).build();
    }
}

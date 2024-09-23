package com.example.javalab;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.List;

//@Path("/hello-world")
//public class HelloResource {
//    @GET
//    @Produces("text/plain")
//    public String hello() {
//        return "Hello, World!";
//    }
//}

@Path("")
public class HelloResource {
    @GET
    @Path("/hello-world")
    @Produces("text/plain")
    public String helloWorld() {
        return "Hello Today!";
    }

//    @GET
//    @Path("/names")
//    @Produces("application/json")
//    public List<String> names() {
//        return List.of("Louise Bugnet", "Hippolyte", "Wasagaming");
//    }

    //Array of products
    @GET
    @Path("/product")
    @Produces("application/json")
//    @Produces(MediaType.APPLICATION.JSON)
    public List<Product> product() {
        return List.of(new Product("New Dawn", 10), new Product("Wasagaming", 9.2));
    }

    //JSON instead of Array of products
    @GET
    @Path("/products")
    @Produces("application/json")
//    @Produces(MediaType.APPLICATION.JSON)
    public Products products() {
        return new Products(List.of(new Product("New Dawn", 10), new Product("Wasagaming", 9.2)), "Updated");
    }
}


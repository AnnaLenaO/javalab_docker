package com.example.javalab.entities;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductList {
    private static List<Product> products;

    public ProductList(List<Product> products) {
        ProductList.products = new CopyOnWriteArrayList<>();
    }

    public static void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }
}

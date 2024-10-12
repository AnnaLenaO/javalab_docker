package com.example.javalab.entities;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductList {
    private final List<Product> products;

    public ProductList(List<Product> products) {
//        this.products = new ArrayList<>(products);
        this.products = new CopyOnWriteArrayList<>();
    }

    public void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }
}

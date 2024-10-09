package com.example.javalab.service;

import com.example.javalab.entities.InputProductData;
import com.example.javalab.entities.Product;
import com.example.javalab.entities.Category;

import java.util.*;

public interface Warehouse {
    Product createNewProduct(InputProductData inputProductData);

    void addNewProduct(InputProductData inputProductData);

    List<Product> getProductList();

    Map<UUID, List<Product>> getProductsPerId();

    Optional<Product> getAProductForItsId(UUID id);

    Map<Category, List<Product>> getProductsPerCategory();

    Record getSortedProductsForACategory(Category category);
}

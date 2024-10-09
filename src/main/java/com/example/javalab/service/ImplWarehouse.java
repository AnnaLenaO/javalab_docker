package com.example.javalab.service;

import com.example.javalab.entities.InputProductData;
import com.example.javalab.entities.Product;
import com.example.javalab.entities.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
@Default
public class ImplWarehouse implements Warehouse {
    private final List<Product> products = Collections.synchronizedList(new ArrayList<>());
    private final List<Product> items = new CopyOnWriteArrayList<>();

    //remove this later
    public ImplWarehouse() {
        System.out.println("ImplWarehouse");
    }

    @Override
    public Product createNewProduct(InputProductData inputProductData) {
        return new Product(
                UUID.randomUUID(),
                inputProductData.name(),
                inputProductData.category(),
                inputProductData.rating(),
                LocalDate.now(),
                LocalDate.now()
        );
    }

    @Override
    public void addNewProduct(InputProductData inputProductData) {
        products.add(createNewProduct(inputProductData));
    }

    @Override
    public List<Product> getProductList() {
        return List.copyOf(items);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private <K, V> Map<K, List<V>> groupingProducts(Function<V, K> function, List<V> listItems) {
        return Collections.unmodifiableMap(
                listItems.stream()
                        .collect(Collectors.groupingBy(function)));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Map<UUID, List<Product>> getProductsPerId() {
        return groupingProducts(Product::id, items);
    }

    @Override
    public Optional<Product> getAProductForItsId(UUID id) {
        Map<UUID, List<Product>> productsPerId = getProductsPerId();
        return productsPerId.entrySet().stream()
                .filter(entry -> entry.getKey().equals(id))
                .flatMap(entry -> entry.getValue().stream())
                .findFirst();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public Map<Category, List<Product>> getProductsPerCategory() {
        return groupingProducts(Product::category, items);
    }

    public record SortedProducts(List<Product> product) {
        public SortedProducts(List<Product> product) {
            this.product = List.copyOf(product);
        }

        public static boolean productHasFirstLetter(Product product) {
            char firstLetter = Character.toLowerCase(getCharAt(product));

            return Character.isLetter(firstLetter) && (firstLetter >= 'a' && firstLetter <= 'z');
        }

        private static char getCharAt(Product product) {
            return product.name().charAt(0);
        }

        public static Comparator<Product> comparingByNameOfProducts() {
            return Comparator.comparing(Product::name);
        }
    }

    @Override
    public SortedProducts getSortedProductsForACategory(Category category) {
        List<Product> productsForACategory = getProductsPerCategory().get(category);

        return productsForACategory.stream()
                .filter(SortedProducts::productHasFirstLetter)
                .sorted(SortedProducts.comparingByNameOfProducts())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), SortedProducts::new)
                );
    }
}

package com.quickiee.backend.service;

import com.quickiee.backend.api.dto.ProductResponse;
import com.quickiee.backend.dto.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = Arrays.asList(
        new Product(1, "Red Apple", "Fresh and juicy red apples, 1kg pack", 120.0, "Fruits", 50,
                "https://example.com/images/red_apple.jpg", true),
        new Product(2, "Banana", "Ripe yellow bananas, 1 dozen", 60.0, "Fruits", 100,
                "https://example.com/images/banana.jpg", true),
        new Product(3, "Full Cream Milk", "Toned cow milk, 1L pack", 45.0, "Dairy", 200,
                "https://example.com/images/milk.jpg", true),
        new Product(4, "Brown Bread", "Whole wheat brown bread, 400g", 35.0, "Bakery", 80,
                "https://example.com/images/bread.jpg", true),
        new Product(5, "Almonds", "Premium almonds, 250g pack", 450.0, "Nuts & Dry Fruits", 40,
                "https://example.com/images/almonds.jpg", true),
        new Product(6, "Orange Juice", "Freshly squeezed orange juice, 1L", 120.0, "Beverages", 60,
                "https://example.com/images/orange_juice.jpg", true)
    );

    public List<ProductResponse> getAllProducts() {
        return products.stream()
            .map(p -> new ProductResponse(
                    p.getId(),
                    p.getName(),
                    p.getDescription(),
                    p.getPrice(),
                    p.getCategory(),
                    p.getImageUrl(),
                    p.isAvailable()
            ))
            .toList();
    }

    public ProductResponse getProductResponseById(int id) {
        Product p = getProductById(id);
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getCategory(),
                p.getImageUrl(),
                p.isAvailable()
        );
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID: " + id));
    }
}

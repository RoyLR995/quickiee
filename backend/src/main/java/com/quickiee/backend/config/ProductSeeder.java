package com.quickiee.backend.config;

import com.quickiee.backend.entity.Product;
import com.quickiee.backend.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductSeeder {

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository) {
        return args -> {

            if (productRepository.count() == 0) {
                productRepository.save(new Product(
                        "Red Apple",
                        "Fresh and juicy red apples, 1kg pack",
                        120.0,
                        "Fruits",
                        50,
                        "/images/red_apple.jpg",
                        true
                ));

                productRepository.save(new Product(
                        "Banana",
                        "Ripe yellow bananas, 1 dozen",
                        60.0,
                        "Fruits",
                        100,
                        "/images/banana.jpg",
                        true
                ));

                productRepository.save(new Product(
                        "Full Cream Milk",
                        "Toned cow milk, 1L pack",
                        45.0,
                        "Dairy",
                        200,
                        "/images/milk.jpg",
                        true
                ));

                productRepository.save(new Product(
                        "Brown Bread",
                        "Whole wheat brown bread, 400g",
                        35.0,
                        "Bakery",
                        80,
                        "/images/bread.jpg",
                        true
                ));

                productRepository.save(new Product(
                        "Almonds",
                        "Premium almonds, 250g pack",
                        450.0,
                        "Nuts & Dry Fruits",
                        40,
                        "/images/almonds.jpg",
                        true
                ));

                productRepository.save(new Product(
                        "Orange Juice",
                        "Freshly squeezed orange juice, 1L",
                        120.0,
                        "Beverages",
                        60,
                        "/images/orange_juice.jpg",
                        true
                ));

                System.out.println("✅ Products seeded successfully");
            }
        };
    }
}

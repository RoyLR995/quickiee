package com.quickiee.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private double price;

    private String category;

    private int stockQuantity;

    private String imageUrl;

    private boolean available;

    public Product() {
        // JPA
    }

    public Product(
            String name,
            String description,
            double price,
            String category,
            int stockQuantity,
            String imageUrl,
            boolean available
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.available = available;
    }

    /* Getters */

    public Integer getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public double getPrice() { return price; }

    public String getCategory() { return category; }

    public int getStockQuantity() { return stockQuantity; }

    public String getImageUrl() { return imageUrl; }

    public boolean isAvailable() { return available; }

    
    public void reduceStock(int qty) {
        this.stockQuantity -= qty;
        if (this.stockQuantity <= 0) {
            this.available = false;
        }
    }
}

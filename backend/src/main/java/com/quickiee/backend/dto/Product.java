package com.quickiee.backend.dto;

public class Product {

    private int id;
    private String name;
    private String description;    
    private double price;
    private String category;       // e.g., Fruits, Dairy, Beverages
    private int stockQuantity;     // available stock
    private String imageUrl;       // for frontend display
    private boolean isAvailable;   // in case the product is temporarily unavailable

    public Product(int id, String name, String description, double price,
                   String category, int stockQuantity, String imageUrl, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getStockQuantity() { return stockQuantity; }
    public String getImageUrl() { return imageUrl; }
    public boolean isAvailable() { return isAvailable; }
}

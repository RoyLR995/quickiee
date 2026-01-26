package com.quickiee.backend.dto;

public class CartItem {

    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public CartItem(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    // Setter for updating quantity
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

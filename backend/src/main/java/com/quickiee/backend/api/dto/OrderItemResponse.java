package com.quickiee.backend.api.dto;

public class OrderItemResponse {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public OrderItemResponse(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // getters
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}

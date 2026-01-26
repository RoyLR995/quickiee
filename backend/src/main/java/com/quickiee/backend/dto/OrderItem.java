package com.quickiee.backend.dto;

public class OrderItem {

    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public OrderItem(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public double getItemTotal() {
        return price * quantity;
    }
}

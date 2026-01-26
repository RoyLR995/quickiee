package com.quickiee.backend.api.dto;

public class CartItemResponse {

    private int id;
    private String name;
    private int quantity;
    private double price;
    private double itemTotal;

    public CartItemResponse(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.itemTotal = price * quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getItemTotal() { return itemTotal; }
}

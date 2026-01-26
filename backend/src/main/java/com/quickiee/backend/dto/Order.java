package com.quickiee.backend.dto;

import java.util.List;

public class Order {

    private int orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status;

    public Order(int orderId, List<OrderItem> items, double totalAmount, String status) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getOrderId() { return orderId; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
}

package com.quickiee.backend.controller;

import com.quickiee.backend.dto.Order;
import com.quickiee.backend.service.OrderService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public Order placeOrder() {
        return orderService.placeOrder();
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }
}

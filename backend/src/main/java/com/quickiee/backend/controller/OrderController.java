package com.quickiee.backend.controller;

import com.quickiee.backend.dto.CartItem;
import com.quickiee.backend.dto.Order;
import com.quickiee.backend.dto.OrderItem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CartController cartController = new CartController();
    private final List<Order> orders = new ArrayList<>();
    private final AtomicInteger orderIdGenerator = new AtomicInteger(1000);

    @PostMapping("/place")
    public Order placeOrder() {

        List<CartItem> cartItems = cartController.viewCart();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem cartItem : cartItems) {
            OrderItem item = new OrderItem(
                    cartItem.getProductId(),
                    cartItem.getProductName(),
                    cartItem.getQuantity(),
                    cartItem.getPrice()
            );
            orderItems.add(item);
            totalAmount += item.getItemTotal();
        }

        Order order = new Order(
                orderIdGenerator.incrementAndGet(),
                orderItems,
                totalAmount,
                "PLACED"
        );

        orders.add(order);

        // Clear cart after order placement
        cartItems.clear();

        return order;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }
}

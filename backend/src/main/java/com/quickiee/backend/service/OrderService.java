package com.quickiee.backend.service;

import com.quickiee.backend.dto.CartItem;
import com.quickiee.backend.dto.Order;
import com.quickiee.backend.dto.OrderItem;
import com.quickiee.backend.exception.BadRequestException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    private final CartService cartService;
    private final List<Order> orders = new ArrayList<>();
    private final AtomicInteger orderIdGenerator = new AtomicInteger(1000);

    public OrderService(CartService cartService) {
        this.cartService = cartService;
    }

    public Order placeOrder() {

        if (cartService.isCartEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem cartItem : cartService.getCartItems()) {
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
        cartService.clearCart();

        return order;
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}

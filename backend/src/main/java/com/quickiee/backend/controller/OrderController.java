package com.quickiee.backend.controller;

import com.quickiee.backend.api.dto.OrderItemResponse;
import com.quickiee.backend.api.dto.OrderResponse;
import com.quickiee.backend.api.dto.UserResponse;
import com.quickiee.backend.entity.Order;
import com.quickiee.backend.repository.OrderRepository;
import com.quickiee.backend.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepo;

    public OrderController(OrderService orderService,
                           OrderRepository orderRepo) {
        this.orderService = orderService;
        this.orderRepo = orderRepo;
    }

    // Get all orders
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderRepo.findAll().stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    // Place a new order
    @PostMapping("/place")
    public OrderResponse placeOrder(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderService.placeOrder(userId);
        return mapToOrderResponse(order);
    }

    @PutMapping("/{orderId}/cancel")
    public OrderResponse cancelOrder(
            @PathVariable Long orderId,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderService.cancelOrder(orderId, userId);
        return mapToOrderResponse(order);
    }

    // Get single order by ID
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId,
                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        return mapToOrderResponse(order);
    }

    // Helper method to convert entity -> DTO
    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus().name());
        response.setTotalAmount(order.getTotalAmount());

        // Map user
        response.setUser(new UserResponse(
                order.getUser().getId(),
                order.getUser().getName(),
                order.getUser().getEmail()
        ));

        // Map order items
        List<OrderItemResponse> items = order.getItems().stream()
                .map(i -> new OrderItemResponse(
                        i.getProductId(),
                        i.getProductName(),
                        i.getQuantity(),
                        i.getPrice()
                ))
                .toList();

        response.setItems(items);
        return response;
    }
}

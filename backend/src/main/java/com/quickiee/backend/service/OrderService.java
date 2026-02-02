package com.quickiee.backend.service;

import com.quickiee.backend.entity.CartItem;
import com.quickiee.backend.entity.Order;
import com.quickiee.backend.entity.OrderItem;
import com.quickiee.backend.entity.Product;
import com.quickiee.backend.entity.User;
import com.quickiee.backend.exception.BadRequestException;
import com.quickiee.backend.repository.OrderRepository;
import com.quickiee.backend.repository.ProductRepository;
import com.quickiee.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartService cartService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            CartService cartService,
            UserRepository userRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order placeOrder(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (cartService.isCartEmpty(user)) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem cartItem : cartService.getCartItems(user)) {

            Product product = productRepository
                .findByIdForUpdate(cartItem.getProduct().getId());

            int requiredQty = cartItem.getQuantity();
            if (product.getStockQuantity() < requiredQty) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }
            product.reduceStock(requiredQty);

            OrderItem item = new OrderItem(
                    product.getId(),
                    product.getName(),
                    requiredQty,
                    product.getPrice()
            );

            item.setOrder(order); // 🔥 REQUIRED

            orderItems.add(item);
            totalAmount += item.getItemTotal();
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(user.getId());

        return savedOrder;
    }
}

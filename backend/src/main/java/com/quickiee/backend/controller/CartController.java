package com.quickiee.backend.controller;

import com.quickiee.backend.entity.CartItem;
import com.quickiee.backend.entity.Product;
import com.quickiee.backend.entity.User;
import com.quickiee.backend.repository.CartRepository;
import com.quickiee.backend.repository.ProductRepository;
import com.quickiee.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public CartController(CartRepository cartRepo,
                          ProductRepository productRepo,
                          UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    private User getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /** 1️⃣ List all items in cart */
    @GetMapping
    public List<CartItem> getCart(@RequestHeader("X-USER-ID") Long userId) {
        User user = getUser(userId);
        return cartRepo.findByUser(user);
    }

    /** 2️⃣ Add to cart (increment if exists) */
    @PostMapping("/add")
    @Transactional
    public CartItem addToCart(@RequestParam int productId,
                              @RequestHeader("X-USER-ID") Long userId) {

        User user = getUser(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if product already in cart
        CartItem cartItem = cartRepo.findByUserAndProduct(user, product).orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1); // increment
        } else {
            cartItem = new CartItem(user, product, 1); // new item with quantity 1
        }

        return cartRepo.save(cartItem);
    }

    /** 3️⃣ Decrement quantity (remove if 0) */
    @PostMapping("/decrement")
    @Transactional
    public CartItem decrementCart(@RequestParam int productId,
                                  @RequestHeader("X-USER-ID") Long userId) {

        User user = getUser(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartRepo.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Item not in cart"));

        int newQty = cartItem.getQuantity() - 1;

        if (newQty <= 0) {
            cartRepo.delete(cartItem); // remove item if quantity 0
            return null;
        } else {
            cartItem.setQuantity(newQty);
            return cartRepo.save(cartItem);
        }
    }
}

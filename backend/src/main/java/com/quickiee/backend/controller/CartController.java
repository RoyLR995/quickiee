package com.quickiee.backend.controller;

import com.quickiee.backend.dto.CartItem;
import com.quickiee.backend.dto.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    // In-memory cart storage
    private final List<CartItem> cart = new ArrayList<>();

    // Reference products from ProductController (simulate DB)
    private final List<Product> products = new ProductController().getProducts();

    // Add product to cart
    @PostMapping("/add/{productId}")
    public CartItem addToCart(@PathVariable int productId, @RequestParam(defaultValue = "1") int quantity) {
        Product product = products.stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if already in cart
        CartItem existing = cart.stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return existing;
        }

        CartItem item = new CartItem(product.getId(), product.getName(), quantity, product.getPrice());
        cart.add(item);
        return item;
    }

    // View cart
    @GetMapping
    public List<CartItem> viewCart() {
        return cart;
    }

    // Remove product from cart
    @DeleteMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable int productId) {
        boolean removed = cart.removeIf(item -> item.getProductId() == productId);
        if (removed) {
            return "Product removed from cart";
        } else {
            return "Product not found in cart";
        }
    }
}

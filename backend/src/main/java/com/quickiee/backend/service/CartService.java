package com.quickiee.backend.service;

import com.quickiee.backend.dto.CartItem;
import com.quickiee.backend.dto.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final ProductService productService;
    private final List<CartItem> cart = new ArrayList<>();

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public CartItem addToCart(int productId, int quantity) {
        Product product = productService.getProductById(productId);

        CartItem existingItem = cart.stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return existingItem;
        }

        CartItem newItem = new CartItem(
                product.getId(),
                product.getName(),
                quantity,
                product.getPrice()
        );

        cart.add(newItem);
        return newItem;
    }

    public List<CartItem> getCartItems() {
        return cart;
    }

    public void removeFromCart(int productId) {
        cart.removeIf(item -> item.getProductId() == productId);
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}

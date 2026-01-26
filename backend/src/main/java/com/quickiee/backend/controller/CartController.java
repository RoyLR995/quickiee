package com.quickiee.backend.controller;

import com.quickiee.backend.dto.CartItem;
import com.quickiee.backend.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{productId}")
    public CartItem addToCart(
            @PathVariable int productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        return cartService.addToCart(productId, quantity);
    }

    @GetMapping
    public List<CartItem> viewCart() {
        return cartService.getCartItems();
    }

    @DeleteMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable int productId) {
        cartService.removeFromCart(productId);
        return "Product removed from cart";
    }
}

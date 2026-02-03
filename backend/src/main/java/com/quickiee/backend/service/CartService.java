package com.quickiee.backend.service;

import com.quickiee.backend.entity.CartItem;
import com.quickiee.backend.entity.User;
import com.quickiee.backend.repository.*;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepo;

    public CartService(CartRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    public List<CartItem> getCartItems(User user) {
        return cartRepo.findByUser(user);
    }

    public boolean isCartEmpty(User user) {
        return cartRepo.countByUser(user) == 0;
    }

    // @Transactional
    // public void clearCart(User user) {
    //     cartRepo.deleteByUser(user);
    // }

    @Transactional
    public void clearCart(Long userId) {
        cartRepo.deleteByUserId(userId);
    }
}

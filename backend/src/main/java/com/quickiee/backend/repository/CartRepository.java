package com.quickiee.backend.repository;

import com.quickiee.backend.entity.CartItem;
import com.quickiee.backend.entity.Product;
import com.quickiee.backend.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    void deleteByUser(User user);

    long countByUser(User user);

    void deleteByUserId(Long userId);
}

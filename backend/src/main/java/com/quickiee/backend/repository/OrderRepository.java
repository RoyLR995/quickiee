package com.quickiee.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quickiee.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    
}

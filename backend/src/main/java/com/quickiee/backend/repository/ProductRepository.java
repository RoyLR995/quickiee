package com.quickiee.backend.repository;

import com.quickiee.backend.entity.Product;

import jakarta.persistence.LockModeType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
     // Fetch products by category
    List<Product> findByCategoryIgnoreCase(String category);

    // Search by name or category
    List<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);

    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.available = true")
    List<String> findDistinctCategories();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findByIdForUpdate(@Param("id") int id);
    
}

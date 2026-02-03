package com.quickiee.backend.controller;

import com.quickiee.backend.entity.Product;
import com.quickiee.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String category,
                                        @RequestParam(required = false) String search) {
        if (category != null) {
            return productService.getProductsByCategory(category);
        }
        if (search != null) {
            return productService.searchProducts(search);
        }
        return productService.getAllProducts();
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }
}

package com.example.first_draft.controller;

import com.example.first_draft.entity.Product;
import com.example.first_draft.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/all/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

}

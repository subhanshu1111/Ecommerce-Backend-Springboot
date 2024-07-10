package com.example.first_draft.service;

import com.example.first_draft.dto.ProductNamePriceDTO;
import com.example.first_draft.entity.Product;
import com.example.first_draft.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    public List<ProductNamePriceDTO> findProductsByName(String name) {
        return productRepository.findProductByName(name);
    }
}
package com.example.first_draft.service;


import com.example.first_draft.entity.Product;
import com.example.first_draft.entity.Seller;
import com.example.first_draft.entity.User;
import com.example.first_draft.repository.ProductRepository;
import com.example.first_draft.repository.SellerRepository;
import com.example.first_draft.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Seller createSeller(Seller seller) {
        User user = seller.getUser();
        if (user != null) {
            user = userRepository.save(user);
            seller.setUser(user);
        }
        return sellerRepository.save(seller);
    }

    @Transactional
    public Product addProductToSeller(Long sellerId, Product product) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        product.setSeller(seller);
        Product savedProduct = productService.save(product);
        seller.getProducts().add(savedProduct);
        sellerRepository.save(seller);
        return savedProduct;
    }

    @Transactional
    public void removeProductFromSeller(Long sellerId, Long productId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Product product = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("Seller does not own this product");
        }

        seller.getProducts().remove(product);
        productService.deleteById(productId);
        sellerRepository.save(seller);
    }
    public List<Seller> getAllSellersWithProducts() {
        return sellerRepository.findAll();
    }


    @Transactional
    public Product updateProductForSeller(Long sellerId, Long productId, Product updatedProduct) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Product existingProduct = productService.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!existingProduct.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("Seller does not own this product");
        }


        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setColors(updatedProduct.getColors());
        existingProduct.setSizes(updatedProduct.getSizes());


        return productService.save(existingProduct);
    }
}

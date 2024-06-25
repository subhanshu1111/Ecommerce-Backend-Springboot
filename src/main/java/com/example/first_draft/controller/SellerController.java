package com.example.first_draft.controller;


import com.example.first_draft.entity.Product;
import com.example.first_draft.entity.Seller;
import com.example.first_draft.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/add")
    public Seller addSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    @PostMapping(value = "/{sellerId}/addProduct")
    public Product addProductToSeller(@PathVariable Long sellerId, @RequestBody Product product) {
        return sellerService.addProductToSeller(sellerId, product);
    }
    @DeleteMapping("/{sellerId}/removeProduct/{productId}")
    public ResponseEntity<?> removeProductFromSeller(@PathVariable Long sellerId, @PathVariable Long productId) {
        try {
            sellerService.removeProductFromSeller(sellerId, productId);
            return ResponseEntity.ok().body("Product successfully removed");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public List<Seller> getAllSellersWithProducts() {
        return sellerService.getAllSellersWithProducts();
    }

    @PutMapping("/{sellerId}/updateProduct/{productId}")
    public ResponseEntity<?> updateProductForSeller(
            @PathVariable Long sellerId,
            @PathVariable Long productId,
            @RequestBody Product updatedProduct) {
        try {
            Product product = sellerService.updateProductForSeller(sellerId, productId, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
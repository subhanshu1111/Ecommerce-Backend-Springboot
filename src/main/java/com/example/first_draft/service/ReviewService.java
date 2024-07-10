package com.example.first_draft.service;

import com.example.first_draft.entity.Buyer;
import com.example.first_draft.entity.OrderHistory;
import com.example.first_draft.entity.Product;
import com.example.first_draft.entity.Review;
import com.example.first_draft.repository.BuyerRepository;
import com.example.first_draft.repository.OrderHistoryRepository;
import com.example.first_draft.repository.ProductRepository;
import com.example.first_draft.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public Review addReview(Long buyerId, Long productId, Review review) {
        OrderHistory orderHistory = orderHistoryRepository.findByBuyerId(buyerId);

        if (orderHistory == null) {
            throw new IllegalArgumentException("Buyer has no order history");
        }
        boolean purchasedProduct = orderHistory.getItems().stream().anyMatch(item -> item.getProductId().equals(productId));

        if(!purchasedProduct) {
            throw new IllegalArgumentException("Product has no purchased by buyer");
        }
        Buyer buyer = buyerRepository.findById(buyerId)
                        .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        reviewRepository.findByBuyerIdAndProductId(buyerId, productId)
                        .ifPresent(r->{throw new IllegalArgumentException("Buyer already reviewed product");});
        buyer.addReview(review);
        product.addReview(review);
        review.setVerified(true);
        return reviewRepository.save(review);
    }

}

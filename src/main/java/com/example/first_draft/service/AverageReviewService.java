package com.example.first_draft.service;

import com.example.first_draft.entity.AverageReview;
import com.example.first_draft.entity.Product;
import com.example.first_draft.entity.Review;
import com.example.first_draft.repository.AverageReviewRepository;
import com.example.first_draft.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AverageReviewService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private AverageReviewRepository averageReviewRepository;
    @Transactional
    public void updateAverageReview(Product product) {
        double averageRating = product.getReviews().stream().mapToDouble(Review::getRating).average().orElse(0.0);
        long totalReviews = product.getReviews().size();
        AverageReview averageReview = product.getAverageReview();
        if(averageReview == null){
            averageReview = new AverageReview();
            averageReview.setProduct(product);

        }
        averageReview.setAverageRating(averageRating);
        averageReview.setTotalReviews(totalReviews);

        averageReviewRepository.save(averageReview);
    }

}

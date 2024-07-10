package com.example.first_draft.controller;


import com.example.first_draft.entity.Review;
import com.example.first_draft.entity.ReviewRequest;
import com.example.first_draft.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest reviewRequest) {
        try {
            Review addedReview = reviewService.addReview(
                    reviewRequest.getBuyerId(),
                    reviewRequest.getProductId(),
                    reviewRequest.getReview()
            );
            return new ResponseEntity<>(addedReview, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

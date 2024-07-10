package com.example.first_draft.controller;


import com.example.first_draft.entity.Review;
import com.example.first_draft.entity.ReviewRequest;
import com.example.first_draft.service.ReviewService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    public static final String uploadDirectory = System.getProperty("user.dir") + "/reviewImg";
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addReview(@RequestPart("reviewRequest") ReviewRequest reviewRequest, @RequestPart("images") List<MultipartFile> files) {
        try {
            Review addedReview = reviewService.addReview(
                    reviewRequest.getBuyerId(),
                    reviewRequest.getProductId(),
                    reviewRequest.getReview(),
                    files
            );
            return new ResponseEntity<>(addedReview, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.error("Error handling file upload", e);
            return new ResponseEntity<>(new ErrorResponse("Error handling file upload: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error in addReview", e);
            return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    @Setter
    @Getter
    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

    }




}

package com.example.first_draft.service;
import com.example.first_draft.controller.ReviewController;
import com.example.first_draft.entity.*;
import com.example.first_draft.repository.BuyerRepository;
import com.example.first_draft.repository.OrderHistoryRepository;
import com.example.first_draft.repository.ProductRepository;
import com.example.first_draft.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
        private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AverageReviewService averageReviewService;

    @Transactional
    public Review addReview(Long buyerId, Long productId, Review review, List<MultipartFile> files) throws IOException {

        if (buyerId == null || productId == null || review == null || files == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }
        logger.info("Starting addReview method with buyerId: {}, productId: {}", buyerId, productId);

        try {

        Buyer buyer = buyerRepository.findById(buyerId)
                        .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
        Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        OrderHistory orderHistory = orderHistoryRepository.findByBuyerId(buyerId);
        List<ReviewImage> reviewImages = new ArrayList<>();

            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                String fileExtension = "";
                if (originalFileName != null && originalFileName.contains(".")) {
                    fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                }
                String fileName = UUID.randomUUID().toString() + (fileExtension.isEmpty() ? "" : "." + fileExtension);

                Path uploadPath = Paths.get(ReviewController.uploadDirectory);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);

                logger.info("Saving file: {}", filePath);

                try {
                    Files.write(filePath, file.getBytes());
                } catch (IOException e) {
                    logger.error("Error saving file: {}", filePath, e);
                    throw new IOException("Could not save file: " + fileName, e);
                }

                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setImagePath(fileName);
                reviewImages.add(reviewImage);
            }

        if (orderHistory == null) {
            throw new IllegalArgumentException("Buyer has no order history");
        }

        boolean purchasedProduct = orderHistory.getItems().stream().anyMatch(item -> item.getProductId().equals(productId));

        if(!purchasedProduct) {
            throw new IllegalArgumentException("Product has no purchased by buyer");
        }
        reviewRepository.findByBuyerIdAndProductId(buyerId, productId)
                        .ifPresent(r->{throw new IllegalArgumentException("Buyer already reviewed product");});
        buyer.addReview(review);
        product.addReview(review);
        review.setVerified(true);
        review.setReviewImages(reviewImages);
            Review savedReview = reviewRepository.save(review);
            averageReviewService.updateAverageReview(product);
            logger.info("Review added successfully");
            return savedReview;
        } catch (Exception e) {
            logger.error("Error in addReview method", e);
            throw e;
        }
    }

}

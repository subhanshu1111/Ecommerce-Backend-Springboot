package com.example.first_draft.service;


import com.example.first_draft.entity.Buyer;
import com.example.first_draft.entity.Product;
import com.example.first_draft.entity.ProductQuestion;
import com.example.first_draft.repository.BuyerRepository;
import com.example.first_draft.repository.ProductQuestionRepository;
import com.example.first_draft.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProductQuestionService {
    @Autowired
    private ProductQuestionRepository productQuestionRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductQuestion addProductQuestion(Long buyerId, Long productId, String question) {

        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(()->new RuntimeException("Buyer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));

        ProductQuestion productQuestion = new ProductQuestion();
        productQuestion.setProduct(product);
        productQuestion.setAskedBy(buyer);
        productQuestion.setQuestionText(question);
        productQuestion.setAskDate(new Date());

        product.addQuestion(productQuestion);
        buyer.addAskedQuestion(productQuestion);

        return productQuestionRepository.save(productQuestion);
    }
}

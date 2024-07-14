package com.example.first_draft.service;

import com.example.first_draft.entity.*;
import com.example.first_draft.repository.OrderHistoryRepository;
import com.example.first_draft.repository.ProductAnswerRepository;
import com.example.first_draft.repository.ProductQuestionRepository;
import com.example.first_draft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductAnswerService {

    @Autowired
    private ProductAnswerRepository productAnswerRepository;
    @Autowired
    private ProductQuestionRepository productQuestionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public ProductAnswer save(Long questionId, Long userId, String answer) {
        ProductQuestion productQuestion = productQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = user.getRole().toString();
        Product product = productQuestion.getProduct();

        if (role.equals("BUYER")) {
            Buyer buyer = user.getBuyer();
            if (buyer == null) {
                throw new RuntimeException("User is not a buyer");
            }
            boolean hasPurchasedProduct = orderHistoryRepository.existsByBuyerAndOrdersOrderItemsProductId(buyer, product.getId());

            if (!hasPurchasedProduct) {
                throw new RuntimeException("Buyer has not purchased this product");
            }
        } else if (role.equals("SELLER")) {
            Seller seller = user.getSeller();
            if (seller == null) {
                throw new RuntimeException("User is not a seller");
            }

            if (!product.getSeller().getId().equals(seller.getId())) {
                throw new RuntimeException("Seller does not own this product");
            }
        } else {
            throw new RuntimeException("User role not authorized to answer questions");
        }

        ProductAnswer productAnswer = new ProductAnswer();
        productAnswer.setQuestion(productQuestion);
        productAnswer.setAnsweredBy(user);
        productAnswer.setAnswerText(answer);
        productAnswer.setAnswerDate(new Date());
        productAnswer.setIsSellerAnswer(role.equals("SELLER"));

        return productAnswerRepository.save(productAnswer);
    }


}

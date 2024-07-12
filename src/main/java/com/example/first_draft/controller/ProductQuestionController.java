package com.example.first_draft.controller;

import com.example.first_draft.entity.ProductQuestion;
import com.example.first_draft.service.ProductQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class ProductQuestionController  {


    @Autowired
    private ProductQuestionService productQuestionService;

    @PostMapping("/{buyerId}/product/{productId}")
    public ProductQuestion addProductQuestion(@PathVariable Long buyerId, @PathVariable Long productId, @RequestBody String question){
        return productQuestionService.addProductQuestion(buyerId, productId, question);
    }
}

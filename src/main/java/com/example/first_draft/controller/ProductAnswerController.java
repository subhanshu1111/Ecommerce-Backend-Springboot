package com.example.first_draft.controller;

import com.example.first_draft.entity.ProductAnswer;
import com.example.first_draft.service.ProductAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
public class ProductAnswerController {

    @Autowired
    private ProductAnswerService productAnswerService;

    @PostMapping("/{questionId}/user/{userId}")
    public ProductAnswer create(@PathVariable Long questionId, @PathVariable Long userId, @RequestBody String answer) {
        return productAnswerService.save(questionId, userId, answer);
    }

}

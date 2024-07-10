package com.example.first_draft.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 public class ReviewRequest {
        private Long buyerId;
        private Long productId;
        private Review review;

    }


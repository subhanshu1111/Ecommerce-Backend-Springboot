package com.example.first_draft.repository;

import com.example.first_draft.entity.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {
}

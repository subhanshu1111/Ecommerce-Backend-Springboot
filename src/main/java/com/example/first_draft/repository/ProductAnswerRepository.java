package com.example.first_draft.repository;


import com.example.first_draft.entity.ProductAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAnswerRepository extends JpaRepository<ProductAnswer, Long> {


}

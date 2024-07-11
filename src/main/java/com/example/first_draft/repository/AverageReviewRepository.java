package com.example.first_draft.repository;

import com.example.first_draft.entity.AverageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AverageReviewRepository extends JpaRepository<AverageReview, Long> {


}

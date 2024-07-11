package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AverageReview extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double averageRating;

    private Long totalReviews;


}

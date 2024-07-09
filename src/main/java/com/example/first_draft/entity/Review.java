package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
@Entity
public class Review extends BaseEntity {

    Integer stars;
    String description;

    @ManyToOne
    private Product product;
}

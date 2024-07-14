package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buyer")
@JsonIgnoreProperties({"reviews"})
public class Buyer extends BaseEntity {
    private String accountStatus;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Review> reviews = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id")
    private ShoppingCart shoppingCart;

    @OneToOne(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private OrderHistory orderHistory;

    @OneToMany(mappedBy = "askedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductQuestion> askedQuestions = new ArrayList<>();


    public void addReview(Review review) {
        reviews.add(review);
        review.setBuyer(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setBuyer(null);
    }
    public void addAskedQuestion(ProductQuestion question) {
        askedQuestions.add(question);
        question.setAskedBy(this);
    }

    public void removeAskedQuestion(ProductQuestion question) {
        askedQuestions.remove(question);
        question.setAskedBy(null);
    }

}

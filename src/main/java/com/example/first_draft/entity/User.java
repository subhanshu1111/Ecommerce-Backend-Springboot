package com.example.first_draft.entity;
import com.example.first_draft.common.model.BaseEntity;

import com.example.first_draft.enums.Role;
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
@Table(name = "user")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Seller seller;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Buyer buyer;

    @OneToMany(mappedBy = "answeredBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductAnswer> providedAnswers = new ArrayList<>();


    public void addProvidedAnswer(ProductAnswer answer) {
            providedAnswers.add(answer);
            answer.setAnsweredBy(this);
    }

    public void removeProvidedAnswer(ProductAnswer answer){
        providedAnswers.remove(answer);
        answer.setAnsweredBy(null);
    }

}

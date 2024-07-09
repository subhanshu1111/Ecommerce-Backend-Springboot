package com.example.first_draft.entity;


import com.example.first_draft.common.model.BaseEntity;
import com.example.first_draft.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address extends BaseEntity {
    private String state;
    private String district;
    private String city;
    private String addressLine1;
    private String addressLine2;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


}
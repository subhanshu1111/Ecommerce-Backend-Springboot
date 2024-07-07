package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;

import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;






@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductImage extends BaseEntity {


    private String imagePath;
    private String imageColor;




}

package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseEntity {


    private String sku;
    private String name;
    private Double price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Color> colors;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Size> sizes;


}

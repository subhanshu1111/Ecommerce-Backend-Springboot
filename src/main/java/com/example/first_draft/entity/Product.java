package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Product extends BaseEntity {

    private String sku;
    private String name;
    private Double price;
    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Color> colors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Size> sizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonIgnoreProperties("products")
    private Seller seller;

    public void setColors(List<Color> colors) {
        this.colors.clear();
        if (colors != null) {
            this.colors.addAll(colors);
        }
    }

    public void setSizes(List<Size> sizes) {
        this.sizes.clear();
        if (sizes != null) {
            this.sizes.addAll(sizes);
        }
    }
}

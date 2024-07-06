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
public class Product extends BaseEntity {

    private String name;
    private String brand;
    private String manufacturer;
    private Integer quantity;
    private String quantityType;
    private String description;
    private Double weightInGrams;
    private String sellerSku;
    private Double standardPrice;
    private Double discount;
    private String fullfillmentType;
    private String whatInBox;

    @Column(columnDefinition = "TEXT")
    private String htmlContent;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private List<Color> colors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private List<Size> sizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonIgnore
    private Seller seller;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Banner> banner = new ArrayList<>();


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

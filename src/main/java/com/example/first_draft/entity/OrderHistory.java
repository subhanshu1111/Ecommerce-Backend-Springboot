package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "order_history")
public class OrderHistory extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    @JsonIgnore
    private Buyer buyer;

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "order_history_product_names", joinColumns = @JoinColumn(name = "order_history_id"))
    @Column(name = "product_name")
    private List<String> productNames = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "order_history_sellers", joinColumns = @JoinColumn(name = "order_history_id"))
    @Column(name = "seller")
    private List<String> sellers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "order_history_original_prices", joinColumns = @JoinColumn(name = "order_history_id"))
    @Column(name = "original_price")
    private List<Double> originalPrices = new ArrayList<>();



    public OrderHistory(Buyer buyer) {
        this.buyer = buyer;
        this.orders = new ArrayList<>();
        this.productNames = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.originalPrices = new ArrayList<>();
    }
}
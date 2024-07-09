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

    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderHistoryItem> items = new ArrayList<>();

    public OrderHistory(Buyer buyer) {
        this.buyer = buyer;
        this.orders = new ArrayList<>();
        this.items = new ArrayList<>();
    }


}
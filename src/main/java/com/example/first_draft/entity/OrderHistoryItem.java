package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_history_id", nullable = false)
    @JsonIgnore
    private OrderHistory orderHistory;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "original_price")
    private Double originalPrice;

}

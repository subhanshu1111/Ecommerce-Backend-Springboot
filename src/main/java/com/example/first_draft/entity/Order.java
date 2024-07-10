package com.example.first_draft.entity;

import com.example.first_draft.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_order")
public class Order extends BaseEntity {
   @ManyToOne
   @JoinColumn(name = "buyer_id", nullable = false)
   @JsonIgnore
   private Buyer buyer;

   @ManyToOne
   @JoinColumn(name = "order_history_id")
   @JsonIgnore
   private OrderHistory orderHistory;

   @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<OrderItem> orderItems = new ArrayList<>();

   private Double totalCost;
   private String status;
   private Date orderDate;
   private Date paymentDate;
}

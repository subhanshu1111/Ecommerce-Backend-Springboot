package com.example.first_draft.dto;

import com.example.first_draft.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends BaseEntity {

    private Double totalCost;
    private String status;
    private Date orderDate;
    private Date paymentDate;
    private List<OrderItemDTO> orderItems;
}

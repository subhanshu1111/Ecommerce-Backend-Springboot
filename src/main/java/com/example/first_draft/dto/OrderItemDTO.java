package com.example.first_draft.dto;

import com.example.first_draft.common.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO extends BaseEntity {

    private String productName;
    private String colorName;
    private String size;
    private Integer quantity;
    private Double price;

}

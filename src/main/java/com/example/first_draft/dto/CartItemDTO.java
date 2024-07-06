package com.example.first_draft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItemDTO {
    private Long id;
    private String productName;
    private int quantity;


}

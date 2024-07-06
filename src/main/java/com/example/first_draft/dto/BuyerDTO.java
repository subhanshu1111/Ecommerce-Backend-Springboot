package com.example.first_draft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BuyerDTO {
    private Long id;
    private String accountStatus;
    private ShoppingCartDTO shoppingCart;


}

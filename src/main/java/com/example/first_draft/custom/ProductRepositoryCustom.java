package com.example.first_draft.custom;

import com.example.first_draft.dto.ProductNamePriceDTO;
import com.example.first_draft.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductNamePriceDTO> findProductByName(String name);
}

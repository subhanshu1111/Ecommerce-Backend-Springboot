package com.example.first_draft.custom;

import com.example.first_draft.dto.ProductNamePriceDTO;
import com.example.first_draft.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductNamePriceDTO> findProductByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductNamePriceDTO> cq = cb.createQuery(ProductNamePriceDTO.class);
        Root<Product> product = cq.from(Product.class);
        Predicate namePredicate = cb.like(cb.lower(product.get("name")), "%" + name.toLowerCase() + "%");
        cq.select(cb.construct(ProductNamePriceDTO.class, product.get("name"), product.get("price")))
            .where(namePredicate);

        return entityManager.createQuery(cq).getResultList();
    }
}

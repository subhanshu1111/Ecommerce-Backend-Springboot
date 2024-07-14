package com.example.first_draft.repository;

import com.example.first_draft.entity.Buyer;
import com.example.first_draft.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    OrderHistory findByBuyerId(Long buyerId);
    boolean existsByBuyerAndOrdersOrderItemsProductId(Buyer buyer, Long productId);
}

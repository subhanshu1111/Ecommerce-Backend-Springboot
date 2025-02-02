package com.example.first_draft.repository;

import com.example.first_draft.entity.OrderHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryItemRepository extends JpaRepository<OrderHistoryItem, Long> {
}

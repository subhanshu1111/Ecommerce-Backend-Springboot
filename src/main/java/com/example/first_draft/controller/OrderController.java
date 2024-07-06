package com.example.first_draft.controller;

import com.example.first_draft.dto.OrderDTO;
import com.example.first_draft.dto.OrderItemDTO;
import com.example.first_draft.entity.Order;
import com.example.first_draft.entity.OrderItem;
import com.example.first_draft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderDTO placeOrder(@RequestParam Long buyerId) {
        Order order = orderService.placeOrder(buyerId);
        return convertToOrderDTO(order);
    }

    @PostMapping("/pay")
    public ResponseEntity<OrderDTO> processPayment(@RequestParam Long orderId) {
        Order order = orderService.processPayment(orderId);
        return ResponseEntity.ok(convertToOrderDTO(order));
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setTotalCost(order.getTotalCost());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setPaymentDate(order.getPaymentDate());

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setId(orderItem.getId());
            orderItemDTO.setProductName(orderItem.getProduct().getName());
            orderItemDTO.setColorName(orderItem.getColor() != null ? orderItem.getColor().getName() : null);
            orderItemDTO.setSize(orderItem.getSize() != null ? orderItem.getSize().getSize() : null);
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setPrice(orderItem.getPrice());
            orderItemDTOs.add(orderItemDTO);
        }
        orderDTO.setOrderItems(orderItemDTOs);

        return orderDTO;

    }

}

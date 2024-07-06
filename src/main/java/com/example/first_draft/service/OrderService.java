package com.example.first_draft.service;

import com.example.first_draft.entity.*;
import com.example.first_draft.repository.BuyerRepository;
import com.example.first_draft.repository.OrderHistoryRepository;
import com.example.first_draft.repository.OrderRepository;
import com.example.first_draft.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Transactional
    public Order placeOrder(Long buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        ShoppingCart cart = buyer.getShoppingCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Shopping cart is empty");
        }

        Order order = new Order();
        order.setBuyer(buyer);
        order.setStatus("PENDING");
        order.setOrderDate(new Date());

        double totalCost = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setColor(cartItem.getColor());
            orderItem.setSize(cartItem.getSize());
            orderItem.setQuantity(cartItem.getQuantity());

            Double itemPrice = cartItem.getColor() != null ? cartItem.getColor().getPrice() : cartItem.getProduct().getStandardPrice();
            orderItem.setPrice(itemPrice);

            totalCost += itemPrice * cartItem.getQuantity();

            order.getOrderItems().add(orderItem);
        }

        order.setTotalCost(totalCost);



        Order savedOrder = orderRepository.save(order);


        cart.getCartItems().clear();
       shoppingCartRepository.save(cart);
        return savedOrder;
    }

    @Transactional
    public Order processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order is not in PENDING status");
        }

    /*
        Payment Process LOGIC in the future
    */

        order.setStatus("PAID");
        order.setPaymentDate(new Date());

        Buyer buyer = order.getBuyer();
        OrderHistory orderHistory = buyer.getOrderHistory();
        if (orderHistory == null) {
            orderHistory = new OrderHistory();
            orderHistory.setBuyer(buyer);
            buyer.setOrderHistory(orderHistory);
        }

        order.setOrderHistory(orderHistory);
        orderHistory.getOrders().add(order);

        Order paidOrder = orderRepository.save(order);
        orderHistoryRepository.save(orderHistory);

        return paidOrder;
    }
}
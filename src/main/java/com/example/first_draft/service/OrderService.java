package com.example.first_draft.service;

import com.example.first_draft.entity.*;
import com.example.first_draft.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private ProductRepository productRepository;

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
            Double sizeColorTotal = cartItem.getColor().getPrice() + cartItem.getSize().getPrice();
            Double itemPrice = cartItem.getSize().getPrice() + sizeColorTotal;

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
    public Order processBuyNow(Long buyerId, Long productId, Long colorId, Long sizeId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        Color color = product.getColors().stream()
                .filter(c -> c.getId().equals(colorId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with specific color does not exist"));

        Size size = product.getSizes().stream()
                .filter(s -> s.getId().equals(sizeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with specific size does not exist"));

        Double sizeColorTotal = color.getPrice() + size.getPrice();
        Double tCost = color.getPrice() + sizeColorTotal;
        Order order = new Order();
        order.setBuyer(buyer);
        order.setStatus("PENDING");
        order.setOrderDate(new Date());
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setColor(color);
        orderItem.setSize(size);
        orderItem.setQuantity(1);
        orderItem.setPrice(tCost);
        order.getOrderItems().add(orderItem);
        order.setTotalCost(tCost);

        return orderRepository.save(order);
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
package com.example.first_draft.service;

import com.example.first_draft.entity.*;
import com.example.first_draft.enums.OrderStatus;
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



    private void updateOrderHistory(Buyer buyer, Order order) {
        OrderHistory orderHistory = buyer.getOrderHistory();
        if (orderHistory == null) {
            orderHistory = new OrderHistory(buyer);
            buyer.setOrderHistory(orderHistory);
        }

        order.setOrderHistory(orderHistory);
        orderHistory.getOrders().add(order);

        for (OrderItem item : order.getOrderItems()) {
            OrderHistoryItem historyItem = new OrderHistoryItem();
            historyItem.setOrderHistory(orderHistory);
            historyItem.setProductId(item.getProduct().getId());  // Add this line
            historyItem.setProductName(item.getProduct().getName());
            historyItem.setSellerName(item.getProduct().getSeller().getOrganizationName());
            historyItem.setOriginalPrice(item.getPrice());

            orderHistory.getItems().add(historyItem);
        }

        orderHistoryRepository.save(orderHistory);
    }

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
        order.setStatus(String.valueOf(OrderStatus.PENDING));
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
            Product product = cartItem.getProduct();
            int newQuantity =  product.getQuantity()- cartItem.getQuantity();
            System.out.println(newQuantity);
            if(newQuantity < 0) {
                throw new RuntimeException("Not enough stock for" + product.getName());
            }
            product.setQuantity(newQuantity);
            productRepository.save(product);
        }






        order.setTotalCost(totalCost);



        Order savedOrder = orderRepository.save(order);


        cart.getCartItems().clear();
       shoppingCartRepository.save(cart);
        updateOrderHistory(buyer, order);
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
        order.setStatus(String.valueOf(OrderStatus.PENDING));
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
        Order savedOrder = orderRepository.save(order);
        updateOrderHistory(buyer, savedOrder);

        int newQuantity = product.getQuantity() - 1;
        if(newQuantity < 0) {
            throw new RuntimeException("Not enough stock for" + product.getName());
        }
        product.setQuantity(newQuantity);



        return orderRepository.save(order);
    }



    @Transactional
    public Order processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!String.valueOf(OrderStatus.PENDING).equals(order.getStatus())) {
            throw new RuntimeException("Order is not in PENDING status");
        }

    /*
        Payment Process LOGIC in the future
    */

        order.setStatus(String.valueOf(OrderStatus.PAID));
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
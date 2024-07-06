package com.example.first_draft.service;

import com.example.first_draft.dto.BuyerDTO;
import com.example.first_draft.dto.CartItemDTO;
import com.example.first_draft.dto.ShoppingCartDTO;
import com.example.first_draft.entity.*;
import com.example.first_draft.repository.BuyerRepository;
import com.example.first_draft.repository.ProductRepository;
import com.example.first_draft.repository.ShoppingCartRepository;
import com.example.first_draft.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;


    private @NotNull ShoppingCart createShoppingCartForBuyer(Buyer buyer) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setBuyer(buyer);
        return shoppingCartRepository.save(shoppingCart);
    }
    private BuyerDTO convertToBuyerDTO(Buyer buyer) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(buyer.getShoppingCart().getId());
        shoppingCartDTO.setCartItems(
                buyer.getShoppingCart().getCartItems().stream()
                        .map(this::convertToCartItemDTO)
                        .collect(Collectors.toList())
        );

        return new BuyerDTO(buyer.getId(), buyer.getAccountStatus(), shoppingCartDTO);
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity()
        );
    }

    public List<BuyerDTO> getAllBuyers(){
        List<Buyer> buyers = buyerRepository.findAll();
        return buyers.stream().map(this::convertToBuyerDTO).collect(Collectors.toList());
    }


    @Transactional
    public Buyer createBuyer(@NotNull Buyer buyer) {
        User user = buyer.getUser();
        if (user != null) {
            user = userRepository.save(user);
            buyer.setUser(user);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setBuyer(buyer);
        buyer.setShoppingCart(shoppingCart);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setBuyer(buyer);
        buyer.setOrderHistory(orderHistory);

        return buyerRepository.save(buyer);
    }
    @Transactional
    public CartItem addItemToCart(Long buyerId, Long productId, Long colorId, Long sizeId, int quantity) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        ShoppingCart shoppingCart = buyer.getShoppingCart();
        if (shoppingCart == null) {
            throw new RuntimeException("Buyer does not have a shopping cart");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Color color = product.getColors().stream()
                .filter(c -> c.getId().equals(colorId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with specific color does not exist"));

        Size size = product.getSizes().stream()
                .filter(s -> s.getId().equals(sizeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product with specific size does not exist"));

        return shoppingCartService.addProductToCart(shoppingCart.getId(), productId, color, size, quantity);
    }

    @Transactional
    public CartItem removeItemFromCart(Long buyerId, Long productId) {
        Buyer buyer = buyerRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));
        ShoppingCart shoppingCart = buyer.getShoppingCart();
        if(shoppingCart == null) {
            throw new RuntimeException("Buyer does not have a shopping cart");
        }
        return shoppingCartService.removeProductFromCart(shoppingCart.getId(),productId);
    }

   @Transactional
   public Order placeOrder(Long buyerId){
        return orderService.placeOrder(buyerId);
   }

   @Transactional
    public Order processPayment(Long buyerId){
        return orderService.processPayment(buyerId);
   }
}

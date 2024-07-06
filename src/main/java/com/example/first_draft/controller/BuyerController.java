package com.example.first_draft.controller;


import com.example.first_draft.dto.BuyerDTO;
import com.example.first_draft.entity.Buyer;
import com.example.first_draft.entity.CartItem;
import com.example.first_draft.repository.ShoppingCartRepository;
import com.example.first_draft.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/buyer")
public class BuyerController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @GetMapping("/getAllBuyers")
    public List<BuyerDTO> getAllBuyers() {
        return buyerService.getAllBuyers();
    }
    @PostMapping("/add")
    public Buyer addBuyer(@RequestBody Buyer buyer) {
        return buyerService.createBuyer(buyer);
    }
    @PostMapping("/{buyerId}/cart/add")
    public CartItem addItemToCart(
            @PathVariable Long buyerId,
            @RequestParam Long productId,
            @RequestParam Long colorId,
            @RequestParam Long sizeId,
            @RequestParam int quantity) {
        return buyerService.addItemToCart(buyerId, productId, colorId, sizeId, quantity);
    }

    @PostMapping("/{buyerId}/cart/remove")
    public CartItem removeItemFromCart(
            @PathVariable Long buyerId,
            @RequestParam Long productId) {
        return buyerService.removeItemFromCart(buyerId, productId);
    }
}

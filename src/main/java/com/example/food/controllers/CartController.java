package com.example.food.controllers;

import com.example.food.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @DeleteMapping("/remove-cart-item/{cart-item-id}")
    public ResponseEntity<String> removeCartItem(@PathVariable("cart-item-id") long cartItemId) {
        return cartService.removeCartItem(cartItemId);
    }
}

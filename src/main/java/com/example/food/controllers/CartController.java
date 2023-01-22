package com.example.food.controllers;

import com.example.food.pojos.CartResponse;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @DeleteMapping("/remove-cart-item/{cart-item-id}")
    public ResponseEntity<BaseResponse> removeCartItem(@PathVariable("cart-item-id") long cartItemId) {
        BaseResponse response = cartService.removeCartItem(cartItemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/view-cart")
    public ResponseEntity<CartResponse> viewCartItems() {
        CartResponse cartResponse = cartService.viewCartItems();
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }
}


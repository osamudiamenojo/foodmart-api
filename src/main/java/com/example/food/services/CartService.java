package com.example.food.services;

import com.example.food.dto.CartDto;
import com.example.food.dto.CartItemDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<String> clearCart();

    ResponseEntity<String> removeCartItem(long cartItemId);



}

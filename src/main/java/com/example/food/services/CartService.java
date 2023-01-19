package com.example.food.services;

import com.example.food.restartifacts.BaseResponse;

public interface CartService {
    BaseResponse removeCartItem(long cartItemId);
    BaseResponse addCartItem(Long cartItem);
    BaseResponse reduceCartItemQuantity(Long cartItem);
}

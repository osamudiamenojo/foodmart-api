package com.example.food.services;

import com.example.food.restartifacts.BaseResponse;

public interface CartService {
    BaseResponse removeCartItem(long cartItemId);
}

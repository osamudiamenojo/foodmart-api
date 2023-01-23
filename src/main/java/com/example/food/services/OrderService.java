package com.example.food.services;

import com.example.food.pojos.OrderResponseDto;

public interface OrderService {

    OrderResponseDto viewDetailsOfAParticularOrder(Long orderId);
}

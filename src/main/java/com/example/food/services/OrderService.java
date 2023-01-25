package com.example.food.services;

import com.example.food.pojos.OrderResponseDto;
import com.example.food.pojos.ViewAllOrderResponse;

public interface OrderService {

    OrderResponseDto viewDetailsOfAParticularOrder(Long orderId);

    ViewAllOrderResponse viewAllOrders(Long userId);
}

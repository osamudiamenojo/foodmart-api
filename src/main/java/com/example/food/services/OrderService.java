package com.example.food.services;

import com.example.food.pojos.OrderResponseDto;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.restartifacts.BaseResponse;

public interface OrderService {

    OrderResponseDto viewDetailsOfAParticularOrder(Long orderId);

    BaseResponse viewOrderHistory();

    ViewAllOrderResponse viewAllOrders(Long userId);
}

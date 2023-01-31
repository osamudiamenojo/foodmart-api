package com.example.food.controllers;

import com.example.food.pojos.OrderResponse;
import com.example.food.pojos.OrderResponseDto;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.pojos.ViewAllOrderResponse;
import com.example.food.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> viewDetailsOfAParticularOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.viewParticularOrder(orderId), HttpStatus.ACCEPTED);
    }
    @GetMapping("/order")
    public BaseResponse viewOrderHistory(){
        return orderService.viewOrderHistory();
    }

    @GetMapping("/viewOrder/{userId}")
    public ResponseEntity<ViewAllOrderResponse> viewAllOrders(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.viewAllOrders(userId), HttpStatus.OK);
    }
}

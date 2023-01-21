package com.example.food.controllers;

import com.example.food.pojos.OrderResponseDto;
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
    public ResponseEntity<OrderResponseDto> viewDetailsOfAParticularOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.viewDetailsOfAParticularOrder(orderId), HttpStatus.ACCEPTED);
    }
}

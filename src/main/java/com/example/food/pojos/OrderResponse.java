package com.example.food.pojos;

import com.example.food.Enum.OrderStatus;
import com.example.food.dto.CartItemDto;
import com.example.food.model.Address;
import com.example.food.model.Cart;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.restartifacts.BaseResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderResponse extends BaseResponse {
    private Long id;
    private String imageUrl;
    private Date createdAt;
    private Date modifiedAt;
    private Integer quantity;
    private OrderStatus orderStatus;
    private Users user;
    private Cart cart;
    private Address address;
    private String paymentMethod;
    private BigDecimal deliveryFee;
    private BigDecimal discount;
    private String deliveryMethod;
    private BigDecimal totalOrderPrice;
    private BigDecimal flatRate;
}

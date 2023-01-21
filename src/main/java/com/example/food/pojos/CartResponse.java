package com.example.food.pojos;

import com.example.food.model.Cart;
import com.example.food.restartifacts.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartResponse extends BaseResponse {

    private List<Cart> cartList;

    private long totalCartElements;

    private long totalPages;
}

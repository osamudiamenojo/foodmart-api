package com.example.food.pojos;

import com.example.food.model.CartItem;
import com.example.food.model.Users;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartResponse extends BaseResponse {
    private List<CartItem> cartItemList;
    private int quantity;
    private BigDecimal cartTotal ;
    private Users users;
}

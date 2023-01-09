package com.example.food.dto;

import com.example.food.model.Product;
import com.example.food.model.Users;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDto {

    private Product product;

    private Users user;
}

package com.example.food.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritesDto {
    private Long userId;
    private Long favoriteProductId;
}


package com.example.food.services;

import org.springframework.http.ResponseEntity;

public interface FavoritesService {

    ResponseEntity<String> removeProductFromFavorite (Long productId);

}
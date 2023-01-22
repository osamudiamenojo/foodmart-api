package com.example.food.services;


import com.example.food.pojos.FavouriteProductResponse;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface FavouritesService {
    public BaseResponse addToFavourites(Long productId);

    FavouriteProductResponse viewAFavouriteProduct(Long favouriteId);
}

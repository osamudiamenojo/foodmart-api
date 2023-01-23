package com.example.food.controllers;

import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.FavouritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favourites")
public class FavouritesController {
    private final FavouritesService favouritesService;

    @PostMapping("/{productId}/add")
    public ResponseEntity<BaseResponse> addProductToFavourite(@PathVariable(value = "productId") Long productId) {
        BaseResponse response = favouritesService.addToFavourites(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/view-favourite/{favouriteId}")
    public BaseResponse viewAFavouriteProduct(@PathVariable Long favouriteId){
        return favouritesService.viewAFavouriteProduct(favouriteId);
    }

    @DeleteMapping("/{productId}/remove")
    public ResponseEntity<BaseResponse> removeFromFavourites(@PathVariable(value = "productId") Long productId) {
        BaseResponse response = favouritesService.removeFromFavourites(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

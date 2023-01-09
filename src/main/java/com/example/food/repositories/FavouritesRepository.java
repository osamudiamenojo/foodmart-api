package com.example.food.repositories;

import com.example.food.model.Favourites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    Boolean existsByUsersAndProducts(Users user, Product product);
}

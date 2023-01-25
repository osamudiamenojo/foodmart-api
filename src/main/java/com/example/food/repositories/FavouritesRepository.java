package com.example.food.repositories;

import com.example.food.model.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    Boolean existsByUsersIdAndProductId(Long usersId, Long productId);
}

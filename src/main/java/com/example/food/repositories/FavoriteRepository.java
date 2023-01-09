package com.example.food.repositories;

import com.example.food.model.Favorites;
import com.example.food.model.Product;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {
    Boolean existsByUserAndProduct (Users user, Product product);
    Optional<Favorites> findFavoritesByProduct_ProductIdAndUser_UserId (Long productId, Long userId);
}
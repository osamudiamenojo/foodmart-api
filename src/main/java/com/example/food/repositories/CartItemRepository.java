package com.example.food.repositories;

import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteAllByCart_CartId(long id);
    Optional<CartItem> findByCart_CartIdAndCartItemId(long cartId, long cartItemId);
    List<CartItem> findCartItemByCart_CartId(long cartId);
}

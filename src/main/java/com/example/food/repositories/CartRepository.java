package com.example.food.repositories;

import com.example.food.model.Cart;
import com.example.food.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Page<Cart> findAllByUsersOrderByCartId(Users users, Pageable pageable);
}

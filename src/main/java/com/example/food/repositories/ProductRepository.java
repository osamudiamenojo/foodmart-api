package com.example.food.repositories;

import com.example.food.model.Product;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    Users findByRole(String role);
}

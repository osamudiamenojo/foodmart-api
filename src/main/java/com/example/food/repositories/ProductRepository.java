package com.example.food.repositories;

import com.example.food.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByProductNameContainingIgnoreCase(String filter, Pageable pageable);


    Optional<Product> findByProductId(Long productId);
}

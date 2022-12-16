package com.example.food.repositories;

import com.example.food.model.Category;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}

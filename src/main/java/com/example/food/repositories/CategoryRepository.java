package com.example.food.repositories;

import com.example.food.model.Category;
import com.example.food.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}

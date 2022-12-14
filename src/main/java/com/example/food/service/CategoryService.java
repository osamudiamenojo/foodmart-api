package com.example.food.service;

import com.example.food.pojo.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
}

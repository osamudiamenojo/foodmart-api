package com.example.food.services;

import com.example.food.pojos.models.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    public String createCategory(CategoryDto categoryDto);
}

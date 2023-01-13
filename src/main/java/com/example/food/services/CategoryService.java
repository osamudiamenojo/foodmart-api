package com.example.food.services;

import com.example.food.pojos.models.CategoryDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.stereotype.Service;

//@Service
public interface CategoryService {
     BaseResponse createCategory(CategoryDto categoryDto);
}

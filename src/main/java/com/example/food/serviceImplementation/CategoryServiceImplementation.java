package com.example.food.serviceImplementation;

import com.example.food.model.Category;
import com.example.food.pojo.CategoryDto;
import com.example.food.repositories.CategoryRepository;
import com.example.food.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImplementation implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);
        return categoryDto;
    }
}

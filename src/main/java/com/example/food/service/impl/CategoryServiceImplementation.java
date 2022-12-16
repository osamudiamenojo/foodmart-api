package com.example.food.service.impl;

import com.example.food.exceptions.InstanceAlreadyExistsException;
import com.example.food.model.Category;
import com.example.food.model.Product;
import com.example.food.pojos.models.CategoryDto;
import com.example.food.repositories.CategoryRepository;
import com.example.food.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public String createCategory(CategoryDto categoryDto) {
        Optional<Category> newCategory = Optional.ofNullable(categoryRepository.findByCategoryName(categoryDto.getCategoryName()));
        if (newCategory.isPresent()) {
            throw new InstanceAlreadyExistsException("Category is available");
        }
            Category category = new Category();
            BeanUtils.copyProperties(categoryDto, category);
            categoryRepository.save(category);
            return "New Category Added.";
        }

    }



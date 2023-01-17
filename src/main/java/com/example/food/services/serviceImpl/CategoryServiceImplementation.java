package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Category;
import com.example.food.dto.CategoryDto;
import com.example.food.pojos.CreateCategoryResponse;
import com.example.food.repositories.CategoryRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CategoryService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ResponseCodeUtil responseCodeUtil;
    @Override
    public CreateCategoryResponse createCategory(CategoryDto categoryDto) {
        Optional<Category> newCategory = Optional.ofNullable(categoryRepository.findByCategoryName(categoryDto.getCategoryName()));

        CreateCategoryResponse categoryResponse = CreateCategoryResponse.builder()
                .categoryName(categoryDto.getCategoryName())
                .build();

        if (newCategory.isPresent()) {
            return responseCodeUtil.updateResponseData(categoryResponse, ResponseCodeEnum.ERROR, "Category Already Exists!");
       }
            Category category = new Category();
            BeanUtils.copyProperties(categoryDto, category);
            categoryRepository.save(category);
            return responseCodeUtil.updateResponseData(categoryResponse, ResponseCodeEnum.SUCCESS, "New Category Added");

        }



}



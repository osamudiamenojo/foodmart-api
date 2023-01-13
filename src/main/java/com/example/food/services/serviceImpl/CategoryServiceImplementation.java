package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Category;
import com.example.food.pojos.models.CategoryDto;
import com.example.food.repositories.CategoryRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CategoryService;
import com.example.food.util.ResponseCodeUtil;
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
    public BaseResponse createCategory(CategoryDto categoryDto) {
        Optional<Category> newCategory = Optional.ofNullable(categoryRepository.findByCategoryName(categoryDto.getCategoryName()));

        BaseResponse baseResponse = new BaseResponse();

       if (newCategory.isEmpty()) {
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "Category Does Not Exist");
       }

            Category category = new Category();
            BeanUtils.copyProperties(categoryDto, category);
            categoryRepository.save(category);
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Category Created");

        }



}



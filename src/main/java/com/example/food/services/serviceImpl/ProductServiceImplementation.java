package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Product;
import com.example.food.pojos.models.ProductDto;
import com.example.food.repositories.ProductRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ResponseCodeUtil responseCodeUtil;


    @Override
    public BaseResponse addNewProduct(ProductDto productDto) {
        Optional<Product> newProduct = productRepository.findByProductName(productDto.getProductName());

        BaseResponse baseResponse = new BaseResponse();
        if (newProduct.isEmpty()) {
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "New Product Is Empty");
        }
            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);
            productRepository.save(product);
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Product Has Been Saved");
        }


    }




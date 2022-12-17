package com.example.food.services.serviceImpl;

import com.example.food.exceptions.InstanceAlreadyExistsException;
import com.example.food.model.Product;
import com.example.food.pojos.models.ProductDto;
import com.example.food.repositories.ProductRepository;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public String addNewProduct(ProductDto productDto) {
        Optional<Product> newProduct = productRepository.findByProductName(productDto.getProductName());
        if (newProduct.isPresent()) {
            throw new InstanceAlreadyExistsException("Product Already In Stock!");
        }
            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);
            productRepository.save(product);

            return "New Product Saved!";
        }

    }




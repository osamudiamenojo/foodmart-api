package com.example.food.serviceImplementation;

import com.example.food.model.Category;
import com.example.food.pojo.ProductDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor
class ProductServiceImplementationTest {


    private final ProductServiceImplementation productServiceImplementation;


    @Test

    void addProduct(){
        Category category = new Category();
        category.setCategoryName("Drinks");
        ProductDto product = new ProductDto();
        product.setProductName("Liberty");
        product.setProductPrice(34.00);
        product.setQuantity(20L);
        product.setCategory(category);
        product.setImageUrl("www.test.com");
        assertEquals("Liberty", productServiceImplementation.addNewProduct(product).getProductName());

    }
}
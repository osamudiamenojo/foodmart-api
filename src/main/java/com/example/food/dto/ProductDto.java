package com.example.food.dto;


import com.example.food.restartifacts.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

    @Getter
    @Setter
    @ToString
    public class ProductDto extends BaseResponse {
        private String imageUrl;
        private String productName;
        private Double productPrice;
        private String productDescription;
        private int availableQuantity;
        private Date createdAt;
    }

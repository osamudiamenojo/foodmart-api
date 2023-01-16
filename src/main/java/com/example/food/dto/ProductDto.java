package com.example.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductDto {

    private String imageUrl;
    private String productName;
    private Double price;
    private String productDescription;
    private int quantity;
    private Date createdAt;
    private Date modifiedAt;
}

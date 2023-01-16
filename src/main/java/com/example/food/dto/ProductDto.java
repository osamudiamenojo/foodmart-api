package com.example.food.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String imageUrl;
    private String productName;
    private Double price;
    private String productDescription;
    private int quantity;
    private Date createdAt;
    private Date modifiedAt;
}

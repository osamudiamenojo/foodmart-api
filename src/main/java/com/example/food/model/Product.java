package com.example.food.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Double price;
    private Date createdAt;
    private Date modifiedAt;

}

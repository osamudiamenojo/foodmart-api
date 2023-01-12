package com.example.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Double price;
    private Date createdAt;
    private Date modifiedAt;

}

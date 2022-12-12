package com.example.food.model;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    private Date createdAt;
    private Date modifiedAt;

    @OneToMany
    private List <Product> categoryList;

    @OneToMany
    private List <Product> favoriteCategory;
}

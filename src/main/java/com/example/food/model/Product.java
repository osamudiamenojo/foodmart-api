package com.example.food.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private Double price;

    private String productDescription;

    private int quantity;

    private String imageUrl;

    private Date createdAt;

    private Date modifiedAt;
}

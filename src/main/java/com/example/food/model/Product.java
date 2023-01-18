package com.example.food.model;
import javax.persistence.*;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;


    private double productPrice;

    private String imageUrl;

    private Long quantity;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;
    @ManyToOne
    @JoinColumn(name = "categoryId",insertable = false,updatable = false)
    private Category category;

}

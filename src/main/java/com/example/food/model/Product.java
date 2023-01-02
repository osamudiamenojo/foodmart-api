package com.example.food.model;

import lombok.*;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productImage;
    private Date createdAt;
    private Date modifiedAt;
    private Long quantity;
    private Long price;
    @ManyToOne
    private Category category;
}

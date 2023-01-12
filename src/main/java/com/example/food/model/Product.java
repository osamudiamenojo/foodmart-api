package com.example.food.model;

import lombok.*;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
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

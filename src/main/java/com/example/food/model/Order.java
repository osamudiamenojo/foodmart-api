package com.example.food.model;

import com.example.food.Enum.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    private Long id;
    private String imageUrl;
    @CreatedDate
    private Timestamp orderDate;
    private int quantity;
    @Enumerated
    private OrderStatus orderStatus;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Users user;
    @OneToMany
    @JoinColumn(name= "order_product_id")
    private List<Product> productList;
    @JsonIgnore
    @OneToOne
    private Address address;
    private String paymentMethod;
    private double deliveryFee;
    private double discount;
    private String deliveryMethod;
    private double totalOrderPrice;



}

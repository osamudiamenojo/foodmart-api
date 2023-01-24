package com.example.food.model;

import com.example.food.Enum.OrderStatus;
import com.example.food.Enum.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private Long id;
    private String imageUrl;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date modifiedAt;
    private Integer quantity;
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
    private PaymentMethod paymentMethod;
    private BigDecimal deliveryFee;
    private BigDecimal discount;
    private PaymentMethod deliveryMethod;
    private BigDecimal totalOrderPrice;



}

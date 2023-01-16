package com.example.food.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @OneToOne
    private Product product;

    @Column(name = "quantity")
    private @NotNull int quantity;

    @Column(name = "price")
    private @NotNull double subTotal;

    @ManyToOne
    @JoinColumn(name = "cart_Id", referencedColumnName = "cartId")
    private Cart cart;


}
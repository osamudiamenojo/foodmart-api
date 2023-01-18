package com.example.food.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private int quantity;

    private double cartTotal;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<CartItem> cartItemList;
    @JsonIgnore
    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Users users;
    public Cart(Users users) {
        this.users = users;
    }


}

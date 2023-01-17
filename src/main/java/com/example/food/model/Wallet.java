package com.example.food.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    private Integer walletBalance;
    private Date createdAt;
    private Date modifiedAt;
    @OneToOne
    private Users users;
}

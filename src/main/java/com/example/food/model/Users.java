package com.example.food.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersId;
    private String baseCurrency;
    private Integer dateOfBirth;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdAt;
    private Date modifiedAt;
    private String role;

    @OneToOne
    private Wallet wallet;

    @OneToMany
    private List <Product> productList;

    @OneToMany
    private List <Product> favouriteList;

   @OneToMany
    private List <Message> messages;


}

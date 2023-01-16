package com.example.food.model;

import com.example.food.Enum.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersId;
    private String baseCurrency;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdAt;
    private Date modifiedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private Wallet wallet;

    @OneToMany
    private List <Product> productList;

    @OneToMany
    private List <Product> favouriteList;

    @OneToMany
    private List <Message> messages;


}

package com.example.food.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String addressName;
    private String phoneNumber;
    private String fullName;
    private String deliveryEmail;
    private Boolean isDefaultAddress;
    private Date createdAt;
    private Date modifiedAt;
    @ManyToOne
    //Todo @JoinColumn
    private Users user;

}

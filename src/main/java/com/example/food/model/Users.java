package com.example.food.model;
import com.example.food.Enum.Role;
import jdk.jfr.Timestamp;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@ToString

@AllArgsConstructor
@NoArgsConstructor
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
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date modifiedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    private Wallet wallet;

    @OneToMany
    private List <Product> productList;

    @OneToMany
    private List <Product> favouriteProducts;

    @OneToMany
    private List <Message> messages;

    @OneToOne
    private Cart cart;

}

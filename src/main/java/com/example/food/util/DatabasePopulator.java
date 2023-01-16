package com.example.food.util;

import com.example.food.Enum.Role;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DatabasePopulator {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @PostConstruct
    private void createUser(){
        Users user = new Users();
        user.setFirstName("Liberty");
        user.setLastName("Vincent");
        user.setBaseCurrency("$");
        user.setDateOfBirth(LocalDate.of(2000,03,23));
        user.setEmail("ali@gmail.com");
        user.setPassword(encoder.encode("1234"));
        user.setRole(Role.ROLE_ADMIN);
        userRepository.save(user);


        Users users1 = new Users();
        users1.setFirstName("Cejoy");
        users1.setLastName("John");
        users1.setBaseCurrency("$");
        users1.setDateOfBirth(LocalDate.of(1900,01,20));
        users1.setEmail("cj@gmail.com");
        users1.setPassword(encoder.encode("1234"));
        users1.setRole(Role.ROLE_USER);
        userRepository.save(users1);

        Product product = new Product();
        product.setProductName("Nike");
        product.setPrice(23.0);
        productRepository.save(product);

    }


}

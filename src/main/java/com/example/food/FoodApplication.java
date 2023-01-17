package com.example.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Entity;

@SpringBootApplication
public class FoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodApplication.class, args);
    }

}

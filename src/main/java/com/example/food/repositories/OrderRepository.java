package com.example.food.repositories;

import com.example.food.model.Order;
import com.example.food.model.Users;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUserOrderByIdDesc(Users users);
}

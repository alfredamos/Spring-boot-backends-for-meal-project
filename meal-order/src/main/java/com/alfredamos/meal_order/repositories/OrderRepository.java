package com.alfredamos.meal_order.repositories;

import com.alfredamos.meal_order.entities.Order;
import com.alfredamos.meal_order.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrdersByUser(User user);
    void deleteOrdersByUser(User user);
}

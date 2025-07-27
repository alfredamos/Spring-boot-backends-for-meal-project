package com.alfredamos.meal_order.repositories;

import com.alfredamos.meal_order.entities.Order;
import com.alfredamos.meal_order.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrdersByUser(User user);

   // @EntityGraph(attributePaths = order.)
   // @Query("SELECT o FROM Order o WHERE o.user = :user")
    void deleteOrdersByUser(@Param("user") User user);
}

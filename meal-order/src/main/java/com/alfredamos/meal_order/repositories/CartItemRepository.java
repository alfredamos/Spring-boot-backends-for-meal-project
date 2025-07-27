package com.alfredamos.meal_order.repositories;

import com.alfredamos.meal_order.entities.CartItem;
import com.alfredamos.meal_order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findAllByOrder(@Param("order")Order order);
}

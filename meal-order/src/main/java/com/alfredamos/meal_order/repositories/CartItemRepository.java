package com.alfredamos.meal_order.repositories;

import com.alfredamos.meal_order.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    @Override
    <S extends CartItem> List<S> saveAll(Iterable<S> entities);
}

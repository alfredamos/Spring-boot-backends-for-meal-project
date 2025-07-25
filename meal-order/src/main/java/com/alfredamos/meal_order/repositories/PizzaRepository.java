package com.alfredamos.meal_order.repositories;

import com.alfredamos.meal_order.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PizzaRepository extends JpaRepository<Pizza, UUID> {
}

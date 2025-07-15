package com.alfredamos.meal_order.repositories;

import com.alfredamos.meal_order.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);
    Boolean existsUserByEmail(String email);
}

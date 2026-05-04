package com.example.carrepairswithticketmanytechmanyspringboo.repositories;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);
    User findByEmail(String email);
}

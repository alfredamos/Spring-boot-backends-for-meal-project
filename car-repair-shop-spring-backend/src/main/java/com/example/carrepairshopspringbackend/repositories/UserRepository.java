package com.example.carrepairshopspringbackend.repositories;

import com.example.carrepairshopspringbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByEmail(String email);

    User getUsersById(UUID id);
}

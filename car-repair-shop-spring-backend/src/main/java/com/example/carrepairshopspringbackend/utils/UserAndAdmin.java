package com.example.carrepairshopspringbackend.utils;

import com.example.carrepairshopspringbackend.entities.Role;
import com.example.carrepairshopspringbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAndAdmin {
    private Role role;
    private User user;

}

package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.UserDto;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserDto deleteUserById(UUID id);
    UserDto getUserById(UUID id);
    List<UserDto> getAllUsers();
    String getUserEmail();
    Role getUserRole();
    UUID getUserId();
}

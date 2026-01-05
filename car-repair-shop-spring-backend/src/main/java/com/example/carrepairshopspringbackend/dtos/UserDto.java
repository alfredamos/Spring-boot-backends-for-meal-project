package com.example.carrepairshopspringbackend.dtos;

import com.example.carrepairshopspringbackend.entities.Gender;
import com.example.carrepairshopspringbackend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;

    private String name;

    private String email;

    private String image;

    private String phone;

    private Gender gender;

    private Role role;

}

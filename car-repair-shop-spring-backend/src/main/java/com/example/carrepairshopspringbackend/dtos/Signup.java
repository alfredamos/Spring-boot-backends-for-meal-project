package com.example.carrepairshopspringbackend.dtos;

import com.example.carrepairshopspringbackend.entities.Gender;
import com.example.carrepairshopspringbackend.entities.Role;
import com.example.carrepairshopspringbackend.validations.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Signup {
    private String address;

    @NotBlank(message = "Name must be valid.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Phone must be valid.")
    private String phone;

    private LocalDate dateOfBirth;

    @ValueOfEnum(enumClass = Gender.class, message = "It must be either Male of Female!")
    private Gender gender;

    @NotBlank(message = "Image must be valid.")
    private String image;

    @NotBlank(message = "Password must be valid.")
    private String password;

    @NotBlank(message = "ConfirmPassword must be valid.")
    private String confirmPassword;

    private Role role;
}


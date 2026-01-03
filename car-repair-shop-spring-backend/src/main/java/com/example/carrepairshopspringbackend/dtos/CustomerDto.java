package com.example.carrepairshopspringbackend.dtos;

import com.example.carrepairshopspringbackend.entities.Gender;
import com.example.carrepairshopspringbackend.validations.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private UUID id;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Phone is required!")
    private String phone;

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "Image is required!")
    private String image;

    @NotBlank(message = "Notes is required!")
    private String notes;

    @ValueOfEnum(enumClass = Gender.class, message = "It must be either Male of Female!")
    private Gender gender;

    private LocalDate dateOfBirth;

    private UUID userId;

}

package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Gender;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.validations.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditUserProfile {
    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Phone is required.")
    private String phone;

    @ValueOfEnum(enumClass = Gender.class, message = "It must be either a Male or Female")
    private Gender gender;

    @NotBlank(message = "Image is required.")
    private String image;

    private LocalDateTime dateOfBirth;

    @NotBlank(message = "Password is required.")
    private String password;

    private Role role;

}


package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.validations.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeUserRole {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @ValueOfEnum(enumClass = Role.class, message = "It must be either User or Admin!")
    private Role role;
}


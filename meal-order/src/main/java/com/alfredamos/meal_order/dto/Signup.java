package com.alfredamos.meal_order.dto;

import com.alfredamos.meal_order.entities.Gender;
import com.alfredamos.meal_order.entities.Role;
import com.alfredamos.meal_order.validations.ValueOfEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Signup {
    @NotBlank(message = "Address must be valid.")
    private String address;

    @NotBlank(message = "Name must be valid.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Phone must be valid.")
    private String phone;

    @ValueOfEnum(enumClass = Gender.class, message = "It must be either Male of Female!")
    private Gender gender;

    @NotBlank(message = "Image must be valid.")
    private String image;

    @NotBlank(message = "Password must be valid.")
    private String password;

    @NotBlank(message = "ConfirmPassword must be valid.")
    private String confirmPassword;

    @ValueOfEnum(enumClass = Role.class, message = "Selection is not in the enum list!")
    private Role role;
}

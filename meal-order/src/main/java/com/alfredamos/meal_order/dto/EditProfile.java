package com.alfredamos.meal_order.dto;

import com.alfredamos.meal_order.entities.Gender;
import com.alfredamos.meal_order.entities.Role;
import com.alfredamos.meal_order.validations.ValueOfEnum;
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
public class EditProfile {
    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Phone is required.")
    private String phone;

    @ValueOfEnum(enumClass = Gender.class, message = "Invalid enum value")
    private Gender gender;

    @NotBlank(message = "Image is required.")
    private String image;

    @NotBlank(message = "Password is required.")
    private String password;

    private Role role;

}

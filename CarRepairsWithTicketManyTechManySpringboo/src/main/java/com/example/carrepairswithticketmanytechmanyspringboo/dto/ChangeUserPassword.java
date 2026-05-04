package com.example.carrepairswithticketmanytechmanyspringboo.dto;

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
public class ChangeUserPassword {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "OldPassword is required.")
    private String password;

    @NotBlank(message = "ConfirmPassword is required.")
    private String confirmPassword;

    @NotBlank(message = "newPassword is required.")
    private String newPassword;
}


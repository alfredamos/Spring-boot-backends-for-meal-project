package com.alfredamos.meal_order.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePassword {
    private String email;
    private String oldPassword;
    private String confirmPassword;
    private String newPassword;
}

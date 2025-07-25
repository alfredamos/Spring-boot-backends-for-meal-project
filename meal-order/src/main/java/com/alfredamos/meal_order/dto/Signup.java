package com.alfredamos.meal_order.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Signup {
    private String address;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    private String image;

    private String password;

    private String confirmPassword;

    private Role role;
}

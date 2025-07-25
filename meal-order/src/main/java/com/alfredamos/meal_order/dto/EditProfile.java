package com.alfredamos.meal_order.entities;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditProfile {
    private String address;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    private String image;

    private String password;

    private Role role;

}

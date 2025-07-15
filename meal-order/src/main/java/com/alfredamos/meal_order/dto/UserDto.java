package com.alfredamos.meal_order.dto;

import com.alfredamos.meal_order.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String name;

    private String address;

    private String email;

    private String phone;

    private String image;

    private Gender gender;
}

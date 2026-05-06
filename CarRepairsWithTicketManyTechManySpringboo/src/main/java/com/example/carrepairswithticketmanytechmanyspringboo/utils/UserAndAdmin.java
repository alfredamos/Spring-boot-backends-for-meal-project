package com.example.carrepairswithticketmanytechmanyspringboo.utils;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAndAdmin {
    private Role role;
    private User user;

}

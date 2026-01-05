package com.example.carrepairshopspringbackend.dtos;

import com.example.carrepairshopspringbackend.entities.Role;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    private String accessToken;
    private Boolean isLoggedIn;
    private Boolean isAdmin;

//    @Override
//    public String toString() {
//        return "Session{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", role=" + role +
//                ", accessToken='" + accessToken + '\'' +
//                ", isLoggedIn=" + isLoggedIn +
//                ", isAdmin=" + isAdmin +
//                '}';
//    }
}

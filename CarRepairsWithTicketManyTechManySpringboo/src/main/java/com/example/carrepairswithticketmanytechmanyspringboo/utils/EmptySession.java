package com.example.carrepairswithticketmanytechmanyspringboo.utils;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.Session;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;

import java.util.UUID;

public class EmptySession {
    public Session session;

    public static Session toEmptySession() {
        return  Session.builder()
                .id(UUID.fromString(""))
                .name("")
                .email("")
                .role(Role.User)
                .isAdmin(false)
                .isLoggedIn(false)
                .accessToken("")
                .build();
    }
}

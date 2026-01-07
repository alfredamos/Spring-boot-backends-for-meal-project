package com.example.carrepairshopspringbackend.controllers;

import com.example.carrepairshopspringbackend.dtos.*;
import com.example.carrepairshopspringbackend.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login request, HttpServletResponse res) {
        var response = authService.loginUser(request, res);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        var response = authService.getCurrentUser();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody ChangePassword request) {
        var response = authService.changeUserPassword(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/change-role")
    public ResponseEntity<?> updateUserRole(@RequestBody ChangeUserRole request) {
        var response = authService.changeUserRole(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/edit-profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody EditProfile request) {
        var response = authService.editUserProfile(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUserToken(@CookieValue(value = "refreshToken") String refreshToken, HttpServletResponse res) {
        var response = authService.RefreshUserToken(refreshToken, res);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody Signup request) {
        var response = authService.signupUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

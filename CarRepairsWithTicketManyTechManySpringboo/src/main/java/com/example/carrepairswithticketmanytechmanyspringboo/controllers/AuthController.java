package com.example.carrepairswithticketmanytechmanyspringboo.controllers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.*;
import com.example.carrepairswithticketmanytechmanyspringboo.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
@EnableJpaAuditing
public class AuthController {
    private final AuthService authService;

    @PatchMapping("/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestBody @Valid ChangeUserPassword request){
        var response = authService.changeUserPassword(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-role")
    public ResponseEntity<?> changeUserRole(@RequestBody @Valid ChangeUserRole changeUserRole, HttpServletRequest request){
        var response = authService.changeUserRole(changeUserRole, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/edit-profile")
    public ResponseEntity<?> editUserProfile(@RequestBody @Valid EditUserProfile editUserProfile){
        var response = authService.editUserProfile(editUserProfile);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request){
        var response = authService.getCurrentUser(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginUser loginUser, HttpServletResponse response){
        var result = authService.loginUser(loginUser, response);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUserToken(@CookieValue(name = "refreshToken") String refreshToken, HttpServletResponse response){
        var result = authService.refreshUserToken(refreshToken, response);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody @Valid SignupUser signupUser){
        var response = authService.signupUser(signupUser);

        return ResponseEntity.ok(response);
    }
}

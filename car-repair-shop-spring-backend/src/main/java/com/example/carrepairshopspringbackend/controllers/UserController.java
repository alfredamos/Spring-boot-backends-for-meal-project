package com.example.carrepairshopspringbackend.controllers;

import com.example.carrepairshopspringbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteUserId(@PathVariable UUID id){
        //----> Delete user by id.
        var response = userService.deleteUserById(id);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserId(@PathVariable UUID id){
        //----> Get user by id.
        var response = userService.getUserById(id);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllUsers(){
        //----> Get all users.
        var response = userService.getAllUsers();

        //----> Return response.
        return ResponseEntity.ok(response);
    }
}

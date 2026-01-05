package com.example.carrepairshopspringbackend.controllers;

import com.example.carrepairshopspringbackend.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class TokenController {

    private final TokenService tokenService;

    @DeleteMapping("/all/delete-all")
    public ResponseEntity<?> deleteAllInvalidTokens() {
        //----> Delete all invalid tokens.
        var response = tokenService.deleteAllInvalidTokens();

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-by-user-id/{userId}")
    public ResponseEntity<?> deleteInvalidTokensByUserId(@PathVariable UUID userId) {
        //----> Delete invalid tokens by user id.
        var response = tokenService.deleteInvalidTokensByUserId(userId);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

}

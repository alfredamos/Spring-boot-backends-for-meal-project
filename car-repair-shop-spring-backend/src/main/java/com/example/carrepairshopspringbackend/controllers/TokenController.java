package com.example.carrepairshopspringbackend.controllers;

import com.example.carrepairshopspringbackend.entities.Token;
import com.example.carrepairshopspringbackend.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/")
    public ResponseEntity<?> createToken(@RequestBody Token token){
        return ResponseEntity.ok(tokenService.createToken(token));
    }

    @GetMapping("/get-token-by-access-token/{accessToken}")
    public ResponseEntity<?> getTokenByAccessToken(@PathVariable String accessToken){
        return ResponseEntity.ok(tokenService.findTokenByAccessToken(accessToken));
    }

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

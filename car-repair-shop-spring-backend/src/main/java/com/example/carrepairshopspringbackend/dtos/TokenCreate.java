package com.example.carrepairshopspringbackend.dtos;

import com.example.carrepairshopspringbackend.entities.TokenType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenCreate {
    @NotBlank(message = "AccessToken is required!")
    private String accessToken;

    @NotBlank(message = "RefreshToken is required!")
    private String refreshToken;

    private boolean revoked = false;

    private boolean expired = false;

    private TokenType tokenType = TokenType.Bearer;

    private UUID userId;

}

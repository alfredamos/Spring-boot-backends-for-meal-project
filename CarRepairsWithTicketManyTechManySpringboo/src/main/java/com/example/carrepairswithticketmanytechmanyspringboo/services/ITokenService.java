package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Token;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;

import java.util.UUID;

public interface ITokenService {
    ResponseMessage createToken(Token token);
    ResponseMessage deleteAllInvalidTokens();
    ResponseMessage deleteInvalidTokensByUserId(UUID userId);
    void revokeAllValidTokensByUserId(UUID userId);
}

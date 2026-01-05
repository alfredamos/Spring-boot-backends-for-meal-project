package com.example.carrepairshopspringbackend.services;

import com.example.carrepairshopspringbackend.entities.Token;
import com.example.carrepairshopspringbackend.repositories.TokenRepository;
import com.example.carrepairshopspringbackend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token createToken(Token token){
        return tokenRepository.save(token);
    }

    public ResponseMessage deleteInvalidTokensByUserId(UUID userId){
        //----> Delete all invalid tokens by user id.
        tokenRepository.deleteAllInvalidTokensByUserId(userId);

        //----> Send back response.
        return new ResponseMessage("All invalid tokens associated with this user have been deleted successfully!", "success", HttpStatus.OK);
    }

    public ResponseMessage deleteAllInvalidTokens(){
        //----> Delete all invalid tokens.
        tokenRepository.deleteAllInvalidTokens();

        //----> Send back response.
        return new ResponseMessage("All invalid tokens are deleted successfully", "success", HttpStatus.OK);
    }
    public Token findTokenByAccessToken(String accessToken){
        return tokenRepository.findTokenByAccessToken(accessToken);
    }

    public void revokedAllTokensByUserId(UUID userId){
        //----> Retrieve all valid tokens.
        var validTokens = findAllValidTokensByUserId(userId);

        //----> Invalidate all tokens associated with this user.
        if (!validTokens.isEmpty()) {
            validTokens.forEach(token -> {
                token.setRevoked(true);
                token.setExpired(true);

            });
            tokenRepository.saveAll(validTokens);
        }
    }

    public List<Token> findAllValidTokensByUserId(UUID userId){
        return tokenRepository.findAllValidTokensByUser(userId);
    }
}

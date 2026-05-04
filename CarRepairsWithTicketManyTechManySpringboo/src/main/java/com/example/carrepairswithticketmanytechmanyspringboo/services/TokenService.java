package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Token;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.TokenRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService{
    private final TokenRepository tokenRepository;

    @Override
    public ResponseMessage createToken(Token token) {
        //----> Save the token in the database.
        tokenRepository.save(token);

        //----> Send back response.
        return ResponseMessage.builder().message("Token created successfully.").status("success").statusCode(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseMessage deleteAllInvalidTokens() {
        //----> Delete all invalid tokens.
        tokenRepository.deleteAllInvalidTokens();

        //----> Send back response.
        return ResponseMessage.builder().message("All invalid tokens deleted successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public ResponseMessage deleteInvalidTokensByUserId(UUID userId) {
        //----> Delete all invalid tokens for the user.
        tokenRepository.deleteAllInvalidTokensByUserId(userId);

        //----> Send back response.
        return ResponseMessage.builder().message("All invalid tokens for the user deleted successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public void revokeAllValidTokensByUserId(UUID userId) {
        //----> Fetch all valid tokens.
        var validTokens = tokenRepository.findAllValidTokensByUser(userId);

        //----> Revoke all valid tokens and store in db.
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validTokens);

    }


}

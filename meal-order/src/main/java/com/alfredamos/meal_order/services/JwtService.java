package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.config.JwtConfig;
import com.alfredamos.meal_order.entities.User;
import com.alfredamos.meal_order.exceptions.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());

    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());

    }

    public Jwt parseToken(String token){
        try{
            var claims = getClaims(token);

            return new Jwt(claims, jwtConfig.getSecretKey());
        }catch (JwtException ex){
            return null;
        }
    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId() == null ? "" : user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .build();


        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token){
        if (token.isEmpty()) {
            throw new UnAuthorizedException("Invalid credential!");

        }

        return (Claims) Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}

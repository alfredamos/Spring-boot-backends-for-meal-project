package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpired(){  return claims.getExpiration().before(new Date());}

    public UUID getUserId(){
        var userId = claims.getSubject();
        return UUID.fromString(userId);
    }

    public String getUserName(){
        return claims.get("name", String.class);
    }

    public Role getUserRole(){
        return Role.valueOf(claims.get("role",String.class));
    }

    public String getUserEmail(){
        return claims.get("email", String.class);
    }

    public String toString(){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

}

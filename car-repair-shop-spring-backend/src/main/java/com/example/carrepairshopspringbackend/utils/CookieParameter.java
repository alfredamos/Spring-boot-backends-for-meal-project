package com.example.carrepairshopspringbackend.utils;

import com.alfredamos.springblog.services.Jwt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CookieParameter {
    private String cookieName;
    private Jwt cookieValue;
    private int expiration;
    private String cookiePath;

}


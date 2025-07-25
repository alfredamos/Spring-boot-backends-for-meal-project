package com.alfredamos.meal_order.filters;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SD {
    private final String accessToken = "accessToken";
    private final String refreshToken = "refreshToken";
    private final String authorization = "Authorization";
    private final String bearer = "Bearer ";
    private final String role = "ROLE_";

}

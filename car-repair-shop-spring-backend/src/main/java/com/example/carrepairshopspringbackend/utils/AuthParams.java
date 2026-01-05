package com.example.carrepairshopspringbackend.utils;


import lombok.Getter;

@Getter
public class AuthParams {
    public final static String accessToken = "accessToken";

    public final static String refreshToken = "refreshToken";

    public final static String session = "session";

    public final static int accessTokenExpiration = 15 * 60 * 1000;

    public final static int refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000;

    public final static int sessionExpiration = 7 * 24 * 60 * 60 * 1000;

    public final static String role = "ROLE_";
    public final static String accessTokenPath = "/";

    public final static String sessionPath = "/";
    public final static String refreshTokenPath = "/api/auth/refresh";

}

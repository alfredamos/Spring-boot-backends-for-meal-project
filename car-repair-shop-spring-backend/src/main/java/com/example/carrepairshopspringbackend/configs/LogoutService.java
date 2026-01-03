package com.example.carrepairshopspringbackend.configs;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //----> Get the access-token from cookies.
        var accessCookies = request.getCookies(); //----> Get all cookies.
        var accessToken = mySpecificCookieValue(accessCookies); //---> Get access-token.

        //----> Get the first valid token.
        var storedAccessToken = tokenService.findTokenByAccessToken(accessToken);

        //----> Invalidate the tokens by setting expire and revoke to true.
        tokenService.revokedAllTokensByUserId(storedAccessToken.getUser().getId());

        //----> Delete cookies
        SetCookie.deleteCookie(AuthParams.accessToken, AuthParams.accessTokenPath, response);
        SetCookie.deleteCookie(AuthParams.refreshToken, AuthParams.refreshTokenPath, response);

    }

    private String mySpecificCookieValue(Cookie[] cookies){
        //----> Check for null cookies.
        if(cookies == null) return "";

        //----> Fetch access-token from cookies.
        return Stream.of(cookies)
                .filter(cookie -> cookie.getName().equals(AuthParams.accessToken))
                .map(Cookie::getValue)
                .findFirst().orElse(null);

    }


}


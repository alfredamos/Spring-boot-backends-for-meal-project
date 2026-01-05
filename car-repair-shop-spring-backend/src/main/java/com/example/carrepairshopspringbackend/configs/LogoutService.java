package com.example.carrepairshopspringbackend.configs;


import com.example.carrepairshopspringbackend.exceptions.UnAuthorizedException;
import com.example.carrepairshopspringbackend.services.TokenService;
import com.example.carrepairshopspringbackend.utils.AuthParams;
import com.example.carrepairshopspringbackend.utils.UserSessionUtil;
import com.example.carrepairshopspringbackend.utils.SetCookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenService tokenService;

    @Override
    public void logout(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Authentication authentication) {
        //----> Retrieve the session from cookies.
        var session = UserSessionUtil.toUserSession(request);

        //---->
        if (session == null) {
            throw new UnAuthorizedException("Invalid token!");
        }

        //----> Invalidate the tokens by setting expiry and revoke to true.
        tokenService.revokedAllTokensByUserId(session.getId());

        //----> Delete cookies
        SetCookie.deleteCookie(AuthParams.accessToken, AuthParams.accessTokenPath, response);
        SetCookie.deleteCookie(AuthParams.refreshToken, AuthParams.refreshTokenPath, response);
        SetCookie.deleteCookie(AuthParams.session, AuthParams.sessionPath, response);

    }

}


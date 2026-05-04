package com.example.carrepairswithticketmanytechmanyspringboo.configs;


import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.UnAuthorizedException;
import com.example.carrepairswithticketmanytechmanyspringboo.services.IAuthService;
import com.example.carrepairswithticketmanytechmanyspringboo.services.ITokenService;
import com.example.carrepairswithticketmanytechmanyspringboo.services.TokenService;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.AuthParams;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.CookieUtil;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.UserSessionUtil;
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
    private final ITokenService tokenService;
    private final IAuthService authService;

    @Override
    public void logout(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Authentication authentication) {
        //----> Get the user session.
        var session = authService.getUserSession(request);

        //---->
        if (session == null) {
            throw new UnAuthorizedException("Invalid token!");
        }

        //----> Invalidate the tokens by setting expiry and revoke to true.
        tokenService.revokeAllValidTokensByUserId(session.getId());

        //----> Delete cookies
        CookieUtil.deleteCookie(AuthParams.accessToken, AuthParams.accessTokenPath, response);
        CookieUtil.deleteCookie(AuthParams.refreshToken, AuthParams.refreshTokenPath, response);

    }

}


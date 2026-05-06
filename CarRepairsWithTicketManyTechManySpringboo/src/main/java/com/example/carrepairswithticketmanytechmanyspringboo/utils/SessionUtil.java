package com.example.carrepairswithticketmanytechmanyspringboo.utils;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.Session;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.AuthRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionUtil {
    private final JwtService jwtService;
    private final AuthRepository authRepository;

    public Session getUserSession(HttpServletRequest request) {
        //----> Get the access token from cookies.
        var accessToken = CookieUtil.getCookie(request, AuthParams.accessToken);

        //----> Check for null access token.
        if(accessToken == null) return EmptySession.toEmptySession();

        //----> Parse the access token.
        var jwt = jwtService.parseToken(accessToken.getValue());

        //----> Check for null and expired jwt.
        if(jwt == null || jwt.isExpired()) return EmptySession.toEmptySession();

        //----> Get the user associated with the jwt from db.
        var user = authRepository.findUserByEmail(jwt.getUserEmail());

        //----> Check for null user.
        if(user == null) return EmptySession.toEmptySession();

        //----> Make user session.
        return Session.builder().
                id(user.getId()).
                email(user.getEmail()).
                role(user.getRole())
                .name(user.getName())
                .isAdmin(user.getRole() == Role.Admin)
                .isLoggedIn(true)
                .build();
    }

}

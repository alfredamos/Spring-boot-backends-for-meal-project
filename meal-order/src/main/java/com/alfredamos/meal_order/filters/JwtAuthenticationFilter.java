package com.alfredamos.meal_order.filters;

import com.alfredamos.meal_order.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        var token = mySpecificCookieValue(cookies);

        if (token.isEmpty()) {
            //throw new UnAuthorizedException("Invalid credential!");
            var authHeader = request.getHeader(AuthParams.authorization);
            token = authHeader.replace(AuthParams.bearer, "");

        }

        var jwt = jwtService.parseToken(token);

        if (jwt == null || jwt.isExpired()){
            filterChain.doFilter(request, response);
            return;
        }

        var role = jwt.getUserRole();
        var email = jwt.getUserEmail();


        var authentication = new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority(AuthParams.role + role))
        );

        authentication.setDetails (new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String mySpecificCookieValue(Cookie[] cookies){
       if (cookies == null || cookies.length == 0) return "";

       Optional<String> specificCookieValue = Stream.of(cookies)
                .filter(cookie -> AuthParams.accessToken.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();

        return  specificCookieValue.orElse(null);
    }
}

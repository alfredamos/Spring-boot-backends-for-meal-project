package com.example.carrepairswithticketmanytechmanyspringboo.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.ToString;

@ToString
public class CookieUtil {
    static public void setCookie(Cookie cookie, String cookiePath, int cookieExpiration, HttpServletResponse response){
        cookie.setHttpOnly(true);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieExpiration);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    static public void setCookie(String cookieValue, String cookieName, String cookiePath, int cookieExpiration, HttpServletResponse response){
       var cookie = new Cookie(cookieName, cookieValue);
       setCookie(cookie, cookiePath, cookieExpiration, response);
    }

    ////----> Delete cookie method.
    static public void deleteCookie(String cookieName, String cookiePath, HttpServletResponse response) {
        //----> Set cookie.
        var cookie = getCookie(cookieName, null);

        cookie.setHttpOnly(true);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(0);
        cookie.setSecure(false);

        //----> Set cookie on response.
        response.addCookie(cookie);
    }

    static public Cookie getCookie(String cookieName, String cookieValue) {
        //----> Set cookie.
        return new Cookie(cookieName, cookieValue);
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}

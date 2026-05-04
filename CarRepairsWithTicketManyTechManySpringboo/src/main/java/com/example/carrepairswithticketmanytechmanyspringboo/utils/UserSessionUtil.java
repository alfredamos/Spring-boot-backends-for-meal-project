package com.example.carrepairswithticketmanytechmanyspringboo.utils;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.Session;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.JsonProcessingException;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class UserSessionUtil {
    public static String toEncodedString(Session session, HttpServletResponse response) {
        try{
            //----> Map session into a JSON.
            var gson = new Gson();
            var sessionString = gson.toJson(session);

            //----> Convert into a bytes.
            byte[] inputBytes = sessionString.getBytes(StandardCharsets.UTF_8);

            //----> Get the Base64 encoder
            var encoder = Base64.getEncoder();

            //----? Encode the bytes to a Base64 string and send it back
            return encoder.encodeToString(inputBytes);
        } catch (Exception e) {
            throw new JsonProcessingException(e.getMessage()) ;
        }

    }

    public static Session toUserSession(HttpServletRequest request) {
        try {
            //----> Retrieve the decoded session cookie from the request.
            var encodedString = Objects.requireNonNull(CookieUtil.getCookie(request, AuthParams.session)).getValue();

            //----> Decode the Base64 string to bytes
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);

            //----> Convert the bytes to a string
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

            //----> Convert the string to a Session object and return it.
            var gson = new Gson();
            return gson.fromJson(decodedString, Session.class);
        } catch (Exception e) {
            throw new JsonProcessingException(e.getMessage());
        }

    }

}

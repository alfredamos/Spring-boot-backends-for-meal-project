package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.*;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface IAuthService {
    ResponseMessage changeUserPassword(ChangeUserPassword changePassword);
    ResponseMessage changeUserRole(UUID id, HttpServletRequest request);
    ResponseMessage editUserPassword(EditUserProfile editProfile);
    UserDto getCurrentUser(HttpServletRequest request);
    Session getUserSession(HttpServletRequest request);
    Session loginUser(LoginUser login, HttpServletResponse response);
    Session logoutUser(HttpServletRequest request, HttpServletResponse response);
    Session refreshUserSession(String refreshUserToken, HttpServletResponse response);
    ResponseMessage signupUser(SignupUser signup);

}

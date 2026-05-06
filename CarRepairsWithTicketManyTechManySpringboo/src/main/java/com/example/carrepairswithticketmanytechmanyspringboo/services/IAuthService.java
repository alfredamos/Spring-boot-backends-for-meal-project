package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.*;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    ResponseMessage changeUserPassword(ChangeUserPassword changePassword);
    ResponseMessage changeUserRole(ChangeUserRole changeUserRole, HttpServletRequest request);
    ResponseMessage editUserProfile(EditUserProfile editProfile);
    UserDto getCurrentUser(HttpServletRequest request);
    Session getUserSession(HttpServletRequest request);
    Session loginUser(LoginUser login, HttpServletResponse response);
    Session refreshUserToken(String refreshUserToken, HttpServletResponse response);
    ResponseMessage signupUser(SignupUser signup);

}

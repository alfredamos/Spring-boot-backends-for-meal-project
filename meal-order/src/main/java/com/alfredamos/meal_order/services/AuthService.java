package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.config.JwtConfig;
import com.alfredamos.meal_order.dto.*;
import com.alfredamos.meal_order.entities.User;
import com.alfredamos.meal_order.exceptions.BadRequestException;
import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.exceptions.UnAuthorizedException;
import com.alfredamos.meal_order.filters.AuthParams;
import com.alfredamos.meal_order.mapper.AuthMapper;
import com.alfredamos.meal_order.mapper.UserMapper;
import com.alfredamos.meal_order.repositories.AuthRepository;
import com.alfredamos.meal_order.repositories.UserRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Data
@AllArgsConstructor
@Service
public class AuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final JwtConfig JwtConfig;


    public ResponseMessage changePassword(ChangePassword changePassword){
        //----> Extract email,oldPassword, confirmPassword and newPassword.
        var email = changePassword.getEmail();
        var oldPassword = changePassword.getOldPassword();
        var confirmPassword = changePassword.getConfirmPassword();
        var newPassword = changePassword.getNewPassword();

        //----> Check for match between confirm-password and new-password.
        checkPasswordMatch(newPassword, confirmPassword);

        //----> Check for the existence of user.
        var user = foundUserByEmail(email, AuthActionType.edit);

        //----> Check for correct password.
        checkForCorrectPassword(oldPassword, user.getPassword());

        //----> Hash password.
        var hashedPassword = this.passwordEncoder.encode(newPassword);

        //----> Replace the old hashed password with new one.
        user.setPassword(hashedPassword);

        //----> Save the new password in the database.
        authRepository.save(user);


        return new ResponseMessage("Success", "Password has been changed successfully!", 200);
    }

    public ResponseMessage editProfile(EditProfile editProfile){
        //----> Get the email and password from the payload.
        var email = editProfile.getEmail();
        var password = editProfile.getPassword();

        //----> Check for the existence of user.
        var user = foundUserByEmail(email, AuthActionType.edit);

        //----> Check for correct password.
        checkForCorrectPassword(password, user.getPassword());

        //----> save the change in profile into the database.
        var userPayload = this.authMapper.toEntity(editProfile);
        //var userPayload = this.getUserFromEditProfile(editProfile, user);
        userPayload.setId(user.getId());
        authRepository.save(userPayload);

        return new ResponseMessage("Success", "Your profile is edited successfully!", 200);
    }

    public ResponseMessage signup(Signup signup) {
        //----> Get the email, password and confirm-password from the payload.
        var email = signup.getEmail();
        var password = signup.getPassword();
        var confirmPassword = signup.getConfirmPassword();

        //----> Check for match between confirm-password and password.
       checkPasswordMatch(password, confirmPassword);

        //----> Check for the existence of user.
        foundUserByEmail(email, AuthActionType.create);

        //----> Check for
        String hashedPassword = passwordEncoder.encode(password);

        //----> Set the hashed password on signup.
        signup.setPassword(hashedPassword);

        //----> Get user payload from signup.
        var userPayload = this.authMapper.toEntity(signup);

        //----> Save the new user in the database.
        authRepository.save(userPayload);

        // Save username and hashedPassword to your database
        return new ResponseMessage("Success", "Signup is successful!", 201);
    }

    private void checkPasswordMatch(String password, String confirmPassword){
        //----> Check for match between confirm-password and password.
        var isMatch = confirmPassword.equals(password);

        if (!isMatch){
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must match!");
            throw new BadRequestException("Password must match!");
        }
    }

    private void checkForCorrectPassword(CharSequence rawPassword, String storedPassword){
        var isCorrectPassword = this.passwordEncoder.matches(rawPassword, storedPassword);
        if (!isCorrectPassword){
            throw new UnAuthorizedException("Invalid credential!");
        }
    }

    private User foundUserByEmail(String email, String mode){
        var user = this.authRepository.findUserByEmail(email);
        if (mode.equalsIgnoreCase(AuthActionType.edit)) {
            if (user == null) {
                throw new NotFoundException("Invalid credential!");
            }
        } else if (mode.equalsIgnoreCase(AuthActionType.create)) {
            if (user != null) {
                throw new NotFoundException("Invalid credential!");
            }
        }

        return user;
    }

    public Jwt getLoginAccess(Login login, HttpServletResponse response) {
        //----> Authenticate user.
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));

        //----> Get the authenticated user.
        var user = this.userService.getUserByEmail(login.getEmail());

        //----> Get access token.
        var accessToken = this.jwtService.generateAccessToken(user);

        //----> Put the access-token in the access-cookie.
        var accessCookie = makeCookie(new CookieParameter(AuthParams.accessToken, accessToken, (int)this.jwtConfig.getAccessTokenExpiration(), AuthParams.accessTokenPath
        ));

        //----> Get refresh-token
        var refreshToken = this.jwtService.generateRefreshToken(user);

        //----> Put the refresh-token in refresh-cookie.
        var refreshCookie = makeCookie(new CookieParameter(AuthParams.refreshToken, refreshToken, (int)this.jwtConfig.getRefreshTokenExpiration(), AuthParams.refreshTokenPath
                ));

        //----> Add the two cookies to response as an object.
        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        return accessToken;
    }

    public void removeLoginAccess(HttpServletResponse response){
        //----> Remove access-cookie.
        var accessCookie = makeCookie(new CookieParameter(AuthParams.accessToken, null, 0, AuthParams.accessTokenPath
        ));

        //----> Add the two cookies to response as an object.
        response.addCookie(accessCookie);


        //----> Remove refresh-cookie.
        var refreshCookie = makeCookie(new CookieParameter(AuthParams.refreshToken, null, 0, AuthParams.refreshTokenPath
        ));

        //----> Add the two cookies to response as an object.
        response.addCookie(refreshCookie);

        response.reset();

    }

    public UserDto getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var email = (String) authentication.getPrincipal();
        var userDto = this.userMapper.toDTO(this.userService.getUserByEmail(email));

        if (userDto == null){
            throw  new NotFoundException("Current user is not found!");
        }

        return userDto;
    }

    public String getRefreshToken(String refreshToken, HttpServletResponse response){
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()){
            throw new UnAuthorizedException("Invalid credentials!");
        }

        var user = this.userRepository.findById(jwt.getUserId()).orElseThrow();

        var accessToken = jwtService.generateAccessToken(user);

        //----> Put the access-token in the access-cookie.
        var accessCookie = makeCookie(new CookieParameter(AuthParams.accessToken, accessToken, (int)this.jwtConfig.getAccessTokenExpiration(), AuthParams.accessTokenPath
        ));

        response.addCookie(accessCookie);

        return  accessToken.toString();
    }

    private Cookie  makeCookie(CookieParameter  cookieParameter){
        //----> Put the access-token in the access-cookie.
        var cookie = new Cookie(cookieParameter.getCookieName(), cookieParameter.getCookieValue() == null ? "" : cookieParameter.getCookieValue().toString());

        cookie.setHttpOnly(true);
        cookie.setPath(cookieParameter.getCookiePath());
        cookie.setMaxAge(cookieParameter.getExpiration()); //----> For seven days.
        cookie.setSecure(false);

        return  cookie;
    }

}

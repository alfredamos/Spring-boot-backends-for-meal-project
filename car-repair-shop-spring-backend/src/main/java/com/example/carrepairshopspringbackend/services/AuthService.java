package com.example.carrepairshopspringbackend.services;

import com.example.carrepairshopspringbackend.dtos.*;
import com.example.carrepairshopspringbackend.entities.Role;
import com.example.carrepairshopspringbackend.entities.Token;
import com.example.carrepairshopspringbackend.entities.TokenType;
import com.example.carrepairshopspringbackend.entities.User;
import com.example.carrepairshopspringbackend.exceptions.BadRequestException;
import com.example.carrepairshopspringbackend.exceptions.ForbiddenException;
import com.example.carrepairshopspringbackend.exceptions.UnAuthorizedException;
import com.example.carrepairshopspringbackend.mapper.AuthMapper;
import com.example.carrepairshopspringbackend.mapper.UserMapper;
import com.example.carrepairshopspringbackend.repositories.UserRepository;
import com.example.carrepairshopspringbackend.utils.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthMapper authMapper;
    private final SameUserAndAdmin sameUserAndAdmin;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ResponseMessage changeUserPassword(ChangePassword request){
        //-----> Destructure the request payload.
        var email = request.getEmail();
        var password = request.getPassword();
        var newPassword = request.getNewPassword();
        var confirmPassword = request.getConfirmPassword();

        //----> Check password match.
        if(passwordNotMatch(newPassword, confirmPassword)){
            throw new BadRequestException("Passwords do not match!");
        }

        //----> Check for existence of user.
        var user = getUserByEmail(email);

        //----> Check for valid password.
        if(isNotValidPassword(password, user.getPassword())){
            throw new UnAuthorizedException("Invalid credential!");
        }

        //----> Hash the new password and set it on the user.
        var encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setId(user.getId());

        //----> Update user in db.
        userRepository.save(user);

        //----> Return response message.
        return new ResponseMessage("Password changed successfully!", "success", HttpStatus.OK);
    }

    public ResponseMessage editUserProfile(EditProfile request){
        //----> Destructure editProfile payload.
        var email = request.getEmail();
        var password = request.getPassword();

        //----> Check for existence of user.
        var user = getUserByEmail(email);

        //----> Check for valid password.
        if (isNotValidPassword(password, user.getPassword())) {
            throw new UnAuthorizedException("Invalid credential!");
        }

        //----> Get the user entity from mapper.
        var userToEdit = authMapper.toEntity(request);
        userToEdit.setPassword(user.getPassword());
        userToEdit.setId(user.getId());
        userToEdit.setRole(user.getRole());
        userToEdit.setUpdatedAt(LocalDateTime.now());

        //----> Update user in db.
        userRepository.save(userToEdit);

        //----> Return response message.
        return new ResponseMessage("Profile updated successfully!", "success", HttpStatus.OK);
    }

    public ResponseMessage changeUserRole(ChangeUserRole request){
        //----> Check for admin privilege, only an admin is permitted to change a role of another user.
        if (!sameUserAndAdmin.checkForAdmin()) {
            throw new ForbiddenException("You are not permitted to perform this operation!");
        }

        //----> Get user email from the request payload.
        var email = request.getEmail();

        //----> Check for existence of user.
        var userToChange = getUserByEmail(email);
        var userRole = userToChange.getRole();

        //----> Change role based on a user role.
        var changedRole = isAdmin(userRole) ? Role.User : Role.Admin;

        userToChange.setRole(changedRole);
        userToChange.setId(userToChange.getId());
        userToChange.setUpdatedAt(LocalDateTime.now());

        //----> Update user in db.
        userRepository.save(userToChange);

        //----> Return response message.
        return new ResponseMessage("Role changed successfully!", "success", HttpStatus.OK);


    }

    public UserDto getCurrentUser(){
        //----> Get the current user from context.
        return userMapper.toDTO(sameUserAndAdmin.getUserFromContext());
    }

    public Session loginUser(Login request, HttpServletResponse response) {
        //----> Destructure the request payload.
        var email = request.getEmail();
        var password = request.getPassword();

        //----> Check for existence of user.
        var user = getUserByEmail(email);

        //----> Check for valid password.
        if(isNotValidPassword(password, user.getPassword())){
            throw new UnAuthorizedException("Invalid credential!");
        }

        //----> Generate tokens and session and set cookies.
        return generateTokensAndSessionAndSetCookies(user, response);

    }

    public Session RefreshUserToken(String refreshToken, HttpServletResponse response)  {
        //----> Parse the refresh token.
        var jwt = jwtService.parseToken(refreshToken);

        //----> Check for null jwt.
        if(jwt == null){
            throw new UnAuthorizedException("Invalid token!");
        }

        //----> Get the user to refresh tokens for.
        var user = userRepository.findById(jwt.getUserId()).orElseThrow(() -> new UnAuthorizedException("Invalid token!"));

        //----> Generate tokens and session and set cookies.
        return generateTokensAndSessionAndSetCookies(user, response);

    }

    public ResponseMessage signupUser(Signup request){
        //----> Destructure the request payload.
        var email = request.getEmail();
        var password = request.getPassword();
        var confirmPassword = request.getConfirmPassword();

        //----> Check password match.
        if(passwordNotMatch(password, confirmPassword)){
            throw new BadRequestException("Passwords do not match!");
        }

        //----> Check for existence of user.
        var user = userRepository.findUserByEmail(email);
        if(user != null){
            throw new UnAuthorizedException("Invalid credential!");
        }

        //----> Hash the password.
        var encodedPassword = passwordEncoder.encode(password);

        //----> Create user entity from mapper.
        var userToSave = authMapper.toEntity(request);
        userToSave.setPassword(encodedPassword);
        userToSave.setRole(Role.User);
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setUpdatedAt(LocalDateTime.now());

        //----> Save user in db.
        userRepository.save(userToSave);

        //----> Return response message.
        return new ResponseMessage("User created successfully!", "success", HttpStatus.CREATED);

    }

    private Session generateTokensAndSessionAndSetCookies(User user, HttpServletResponse response)  {
        //----> Initialize token object.
        var token = new Token();
        token.setUser(user);

        //----> Revoke valid tokens associated with this user.
        tokenService.revokedAllTokensByUserId(user.getId());

        //----> Generate an access token and set it in a cookie.
        var accessToken = jwtService.generateAccessToken(user);
        var accessCookie = makeCookie(AuthParams.accessToken, accessToken.toString(), AuthParams.accessTokenExpiration, AuthParams.accessTokenPath);
        response.addCookie(accessCookie);
        token.setAccessToken(accessToken.toString());

        //----> Make a session for the user.
        var session = MakeSession(user, accessToken.toString());
        var encodedUserSessionString = UserSessionUtil.toEncodedString(session, response);
        var sessionCookie = makeCookie(AuthParams.session, encodedUserSessionString, AuthParams.sessionExpiration, AuthParams.sessionPath);
        response.addCookie(sessionCookie);

        //----> Generate a refresh token and set it in a cookie.
        var refreshToken = jwtService.generateRefreshToken(user);
        var refreshCookie = makeCookie(AuthParams.refreshToken, refreshToken.toString(), AuthParams.refreshTokenExpiration, AuthParams.refreshTokenPath);
        response.addCookie(refreshCookie);
        token.setRefreshToken(refreshToken.toString());

        //----> Make a new token object.
        var tokenObj = makeNewTokenObject(token);
        tokenService.createToken(tokenObj);

        //----> Return session.
        return session;
    }

    private Session MakeSession(User user, String accessToken){
        return Session.builder().
                accessToken(accessToken).
                name(user.getName()).
                email(user.getEmail()).
                role(user.getRole()).
                isLoggedIn(true).
                isAdmin(user.getRole() == Role.Admin).
                id(user.getId()).
                build();
    }

    private Token makeNewTokenObject(Token token) {
        token.setTokenType(TokenType.Bearer);
        token.setExpired(false);
        token.setRevoked(false);

        return token;
    }

    private Cookie makeCookie(String cookieName, String cookieValue, int cookieExpiration, String cookiePath){
        //----> Set cookie.
        var cookie = new Cookie(cookieName, cookieValue);
        return SetCookie.makeCookie(cookie, cookiePath, cookieExpiration);
    }

    private boolean passwordNotMatch(String passwordOne, String passwordTwo){
        return !passwordOne.equals(passwordTwo);
    }

    private User getUserByEmail(String email){
        //----> Retrieve user by email.
        var user = userRepository.findUserByEmail(email);

        //----> Check for null user.
        if(user == null){
            throw new UnAuthorizedException("Invalid credential!");
        }

        //----> Return user.
        return user;
    }

    private boolean isNotValidPassword(String rawPassword, String encodedPassword){
        return !passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private boolean isAdmin(Role userRole){
        return userRole.equals(Role.Admin);
    }
}


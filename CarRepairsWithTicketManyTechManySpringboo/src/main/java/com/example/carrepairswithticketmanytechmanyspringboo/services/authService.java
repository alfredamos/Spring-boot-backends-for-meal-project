package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.*;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Token;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.TokenType;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.AuthMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.UserMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.AuthRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class authService implements IAuthService{
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;
    private JwtService jwtService;
    private final TokenService tokenService;
    private final UserMapper userMapper;

    @Override
    public ResponseMessage changeUserPassword(ChangeUserPassword changePassword) {
        //----> Check for password match.
        if(isPasswordNotMatch(changePassword.getNewPassword(), changePassword.getConfirmPassword())){
            return ResponseMessage.builder().message("Passwords do not match.").status("error").build();
        }

        //----> Check for existing user.
        var user = findUserByEmail(changePassword.getEmail());

        //----> Check for password validity.
        if(isPasswordNotValid(changePassword.getPassword(), user.getPassword())){
            return ResponseMessage.builder().message("Current password is incorrect.").status("error").build();
        }

        //----> Hash and update new password.
        user.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));

        //----> Save user.
        authRepository.save(user);

        //----> Send back response.
        return ResponseMessage.builder().message("Password changed successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public ResponseMessage changeUserRole(UUID id, HttpServletRequest request) {
        //----> Get the user session.
        var session = getUserSession(request);

        //----> Check for null user session.
        if(session == null) return ResponseMessage.builder().message("User not logged in!").status("error").build();

        //----> User to change role must be an admin.
        if (session.getRole() != Role.Admin) return ResponseMessage.builder().message("Only admin can change user role!").status("error").build();

        //----> Check for existent user.
        var user = authRepository.findById(id).orElse(null);
        if(user == null) return ResponseMessage.builder().message("User not found!").status("error").build();

        //----> Change user role.
        user.setRole(user.getRole() == Role.User ? Role.Admin : Role.User);
        authRepository.save(user);

        //----> Send back response.
        return ResponseMessage.builder().message("User role changed successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public ResponseMessage editUserPassword(EditUserProfile editProfile) {
        //----> Check for existent user.
        var user = findUserByEmail(editProfile.getEmail());

        //----> Check for password validity.
        if(isPasswordNotValid(editProfile.getPassword(), user.getPassword())){
            return ResponseMessage.builder().message("Current password is incorrect.").status("error").build();
        }

        //----> Store the updated user in db.
        var mappedUser = authMapper.toEntity(editProfile);
        mappedUser.setId(user.getId());
        mappedUser.setPassword(user.getPassword());
        mappedUser.setRole(user.getRole());
        authRepository.save(mappedUser);

        //----> Send back response.
        return ResponseMessage.builder().message("Profile updated successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public UserDto getCurrentUser(HttpServletRequest request) {
       //----> Get the user session.
       var userSession = getUserSession(request);

       //----> Check for null user session.
       if(userSession == null) return new UserDto();

       //----> Fetch the current user.
       var user = authRepository.findUserByEmail(userSession.getEmail());

       //----> Check for null user.
       if(user == null) return new UserDto();

       //----> Send back response.
       return userMapper.toDTO(user);
    }

    @Override
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
        return this.makeUserSession(user, accessToken.getValue());
    }

    @Override
    public Session loginUser(LoginUser login, HttpServletResponse response) {
        //----> Check for existent user.
        var user = authRepository.findUserByEmail(login.getEmail());
        if(user == null) return EmptySession.toEmptySession();

        //----> Check for password validity.
        if(isPasswordNotValid(login.getPassword(), user.getPassword())) return EmptySession.toEmptySession();

        //----> Generate tokens and store them in cookies
        return this.generateTokensAndStoreCookies(user, response);
    }

    @Override
    public Session logoutUser(HttpServletRequest request, HttpServletResponse response) {
        //----> Get the user session.
        var session = getUserSession(request);
        if(session == null) return EmptySession.toEmptySession();

        //----> Revoke all valid tokens.
        tokenService.revokeAllValidTokensByUserId(session.getId());

        //----> Delete all tokens in cookies.
        CookieUtil.deleteCookie(AuthParams.accessToken, AuthParams.accessTokenPath, response);
        CookieUtil.deleteCookie(AuthParams.refreshToken, AuthParams.refreshTokenPath, response);

        //----> Send back response.
        return EmptySession.toEmptySession();
    }

    @Override
    public Session refreshUserSession(String refreshUserToken, HttpServletResponse response) {
        //----> Parse the refresh token.
        var jwt = jwtService.parseToken(refreshUserToken);

        //----> Check for null and expired jwt.
        if(jwt == null || jwt.isExpired()) return EmptySession.toEmptySession();

        //----> Get the user associated with the jwt from db.
        var user = authRepository.findUserByEmail(jwt.getUserEmail());

        //----> Check for null user.
        if(user == null) return EmptySession.toEmptySession();

        //----> Generate tokens and store them in cookies
        return this.generateTokensAndStoreCookies(user, response);

    }

    @Override
    public ResponseMessage signupUser(SignupUser signup) {
        //----> Check for password match.
        if(isPasswordNotMatch(signup.getPassword(), signup.getConfirmPassword())){
            return ResponseMessage.builder().message("Passwords do not match.").status("error").build();
        }

        //----> Check for existing user.
        var user = authRepository.findUserByEmail(signup.getEmail());
        if(user != null){
            return ResponseMessage.builder().message("User already exists.").status("error").build();
        }

        //----> Hash the password.
        var encodedPassword = bCryptPasswordEncoder.encode(signup.getPassword());
        signup.setPassword(encodedPassword);

        //----> Store the user in db.
        authRepository.save(authMapper.toEntity(signup));

        //----> Send back response.
        return ResponseMessage.builder().message("User created successfully.").status("success").statusCode(HttpStatus.CREATED).build();
    }

    private Session generateTokensAndStoreCookies(User user, HttpServletResponse response){
        //----> Revoke all valid tokens.
        tokenService.revokeAllValidTokensByUserId(user.getId());

        //----> Generate access token and store it in cookies.
        var accessToken = jwtService.generateAccessToken(user);
        CookieUtil.setCookie(accessToken.toString(), AuthParams.accessToken, AuthParams.accessTokenPath, AuthParams.accessTokenExpiration, response);

        //----> Generate refresh token and store it in cookies.
        var refreshToken = jwtService.generateRefreshToken(user);
        CookieUtil.setCookie(refreshToken.toString(), AuthParams.refreshToken, AuthParams.refreshTokenPath, AuthParams.refreshTokenExpiration, response);

        //----> Store refresh token in db.
        var tokenObj = this.makeTokenObject(accessToken.toString(), refreshToken.toString());
        tokenService.createToken(tokenObj);

        //----> Make user session.
        return this.makeUserSession(user, accessToken.toString());
    }

    private boolean isPasswordNotMatch(String passwordOne, String passwordTwo){
        return !passwordOne.equals(passwordTwo);
    }

    private boolean isPasswordNotValid(String rawPassword, String encodedPassword) {
        return !bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    private User findUserByEmail(String email){
        //----> Fetch user by email.
        var user = authRepository.findUserByEmail(email);

        //----> Check for null user.
        if(user == null){
            throw new NotFoundException("User not found!");
        }

        //----> Send back response.
        return user;
    }

    private Session makeUserSession(User user, String accessToken){
        return  Session.builder()
                .accessToken(accessToken)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .isAdmin(user.getRole() == Role.Admin)
                .isLoggedIn(true)
                .id(user.getId())
                .build();
    }

    private Token makeTokenObject(String accessToken, String refreshToken){
        return Token.builder()
                .accessToken(accessToken)
                .expired(false)
                .revoked(false)
                .tokenType(TokenType.Bearer)
                .refreshToken(refreshToken)
                .build();
    }
}

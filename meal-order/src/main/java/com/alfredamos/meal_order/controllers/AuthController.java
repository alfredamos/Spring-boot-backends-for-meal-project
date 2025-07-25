package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.JwtResponse;
import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.dto.ChangePassword;
import com.alfredamos.meal_order.dto.EditProfile;
import com.alfredamos.meal_order.dto.Login;
import com.alfredamos.meal_order.dto.Signup;
import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.filters.AuthParams;
import com.alfredamos.meal_order.mapper.UserMapper;
import com.alfredamos.meal_order.services.AuthService;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Tag(name = "auth")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;


    @PatchMapping("/change-password")
    public ResponseEntity<ResponseMessage> changePassword(@Valid @RequestBody ChangePassword changePassword){
        var result =  this.authService.changePassword(changePassword);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/edit-profile")
    public ResponseEntity<ResponseMessage> editProfile(@Valid @RequestBody EditProfile editProfile){
        var result = this.authService.editProfile(editProfile);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody Login login, HttpServletResponse response){

        final var accessToken = this.authService.getLoginAccess(login, response);

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(accessToken.toString()));

    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletResponse response){
        //----> Delete access-cookie
        authService.removeLoginAccess(response);

        return ResponseEntity.ok(new ResponseMessage("Success", "Logout successfully!", 200));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = AuthParams.refreshToken) String refreshToken, HttpServletResponse response){
       var accessToken = this.authService.getRefreshToken(refreshToken, response); //----> Get access token.

        return ResponseEntity.ok(new JwtResponse(accessToken));
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@Valid @RequestBody Signup signup){
        var result = this.authService.signup(signup);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var userDto = this.authService.getCurrentUser();

        return ResponseEntity.ok(userDto);
    }

    private UserDto getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var email = (String) authentication.getPrincipal();
        var userDto = this.userMapper.toDTO(this.userService.getUserByEmail(email));

        if (userDto == null){
            throw  new NotFoundException("Current user is not found!");
        }

        return userDto;
    }

}





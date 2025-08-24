package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.JwtResponse;
import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.dto.ChangePassword;
import com.alfredamos.meal_order.dto.EditProfile;
import com.alfredamos.meal_order.dto.Login;
import com.alfredamos.meal_order.dto.Signup;
import com.alfredamos.meal_order.filters.AuthParams;
import com.alfredamos.meal_order.services.AuthService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;


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
    public ResponseEntity<ResponseMessage> login(@Valid @RequestBody Login login, HttpServletResponse response){
        //----> Login
        var loginResponse = this.authService.getLoginAccess(login, response);

        //----> Send back the response.
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletResponse response){
        //----> Delete access-cookie
        authService.removeLoginAccess(response);

        //----> Send back the response.
        return ResponseEntity.ok(new ResponseMessage("Success", "Logout successfully!", 200));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = AuthParams.refreshToken) String refreshToken, HttpServletResponse response){
       //----> Get a refresh-token.
        var accessToken = this.authService.getRefreshToken(refreshToken, response); //----> Get access token.

        //----> Send back the response.
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@Valid @RequestBody Signup signup){
        //----> Signup a new user.
        var result = this.authService.signup(signup);

        //----> Send back the response.
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        //----> Get the current user.
        var userDto = this.authService.getCurrentUser();

        //----> Send back the response.
        return ResponseEntity.ok(userDto);
    }

}





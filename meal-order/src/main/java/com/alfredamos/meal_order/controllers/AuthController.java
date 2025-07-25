package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.entities.ChangePassword;
import com.alfredamos.meal_order.entities.EditProfile;
import com.alfredamos.meal_order.entities.Login;
import com.alfredamos.meal_order.entities.Signup;
import com.alfredamos.meal_order.services.AuthService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Tag(name = "auth")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PatchMapping("/change-password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody ChangePassword changePassword){
        var result =  this.authService.changePassword(changePassword);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/edit-profile")
    public ResponseEntity<ResponseMessage> editProfile(@RequestBody EditProfile editProfile){
        var result = this.authService.editProfile(editProfile);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody Login login){
        var result = this.authService.login(login);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody Signup signup){
        var result = this.authService.signup(signup);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

}





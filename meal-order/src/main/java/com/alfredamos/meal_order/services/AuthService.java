package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.entities.*;
import com.alfredamos.meal_order.mapper.AuthMapper;
import com.alfredamos.meal_order.repositories.AuthRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Data
@AllArgsConstructor
@Service
public class AuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final AuthMapper authMapper;


    public ResponseMessage changePassword(ChangePassword changePassword){
        //----> Extract email,oldPassword, confirmPassword and newPassword.
        var email = changePassword.getEmail();
        var oldPassword = changePassword.getOldPassword();
        var confirmPassword = changePassword.getConfirmPassword();
        var newPassword = changePassword.getNewPassword();

        //----> Check for match between confirm-password and new-password.
        var isMatch = confirmPassword.equals(newPassword);

        if (!isMatch){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must match!");
        }

        //----> Check for the existence of user.
        var user = this.authRepository.findUserByEmail(email);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credential!");
        }

        //----> Check for correct password.
        var isCorrectPassword = this.passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isCorrectPassword){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credential!");
        }

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
        var user = this.authRepository.findUserByEmail(email);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credential!");
        }

        //----> Check for correct password.
        var isCorrectPassword = this.passwordEncoder.matches(password, user.getPassword());
        if (!isCorrectPassword){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credential!");
        }

        //----> save the change in profile into the database.
        var userPayload = this.authMapper.toEntity(editProfile);
        //var userPayload = this.getUserFromEditProfile(editProfile, user);
        userPayload.setId(user.getId());
        authRepository.save(userPayload);

        return new ResponseMessage("Success", "Your profile is edited successfully!", 200);
    }

    public ResponseMessage login(Login login){
        //----> Get the email and password from the payload.
        var email = login.getEmail();
        var password = login.getPassword();

        //----> Check for the existence of user.
        var user = this.authRepository.findUserByEmail(email);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credential!");
        }

        //----> Check for correct password.
        var isCorrectPassword = this.passwordEncoder.matches(password, user.getPassword());
        if (!isCorrectPassword){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credential!");
        }

        return new ResponseMessage("Success", "Login is successful!", 200);
    }

    public ResponseMessage signup(Signup signup) {
        //----> Get the email, password and confirm-password from the payload.
        System.out.println("In authService, signup : " + signup);
        var email = signup.getEmail();
        var password = signup.getPassword();
        var confirmPassword = signup.getConfirmPassword();

        //----> Check for match between confirm-password and password.
        var isMatch = confirmPassword.equals(password);

        if (!isMatch){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must match!");
        }

        //----> Check for the existence of user.
        var user = this.authRepository.findUserByEmail(email);
        if(user != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User exist for these credentials!");
        }

        //----> Check for
        String hashedPassword = passwordEncoder.encode(password);

        //----> Set the hashed password on signup.
        signup.setPassword(hashedPassword);

        //----> Get user payload from signup.
        var userPayload = this.authMapper.toEntity(signup);
        //var userPayload = this.getUserFromSignup(signup);

        System.out.println("In authService, userPayload : " + userPayload);

        //----> Save the new user in the database.
        authRepository.save(userPayload);

        // Save username and hashedPassword to your database
        return new ResponseMessage("Success", "Signup is successful!", 201);
    }

}

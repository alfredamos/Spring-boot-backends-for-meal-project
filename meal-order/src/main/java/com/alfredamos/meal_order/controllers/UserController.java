package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id){
        //----> Get the user by id.
        var userDto = this.userService.getUserById(id);

        //----> Send back the response.
        return new ResponseEntity<>(userDto, HttpStatus.OK);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUserById(@PathVariable UUID id){
        //----> Delete the user with the given id.
        var responseMessage = this.userService.deleteUser(id);

        //----> Send back the response.
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}

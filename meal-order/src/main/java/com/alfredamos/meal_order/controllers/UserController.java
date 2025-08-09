package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.exceptions.ForbiddenException;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@Tag(name = "users")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final OwnerCheck ownerCheck;


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id){
        var isSameUser = ownerCheck.compareAuthUserIdWithParamUserId(id);
        if (!isSameUser){
            throw new ForbiddenException("You are not permitted to view this resource!");
        }
        var userDto = this.userService.getUserById(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUserById(@PathVariable UUID id){
        var isSameUser = ownerCheck.compareAuthUserIdWithParamUserId(id);
        if (!isSameUser){
            throw new ForbiddenException("You are not permitted to delete this resource!");
        }

        var responseMessage = this.userService.deleteUser(id);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}

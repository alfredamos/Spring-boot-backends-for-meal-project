package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.mapper.UserMapper;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Tag(name = "users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapperImpl;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
       var users = this.userService.getAllUsers();

        var usersDto = this.userMapperImpl.toDTOList(users);

       return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id){
        var user = this.userService.getUserById(id).orElse(null);

        var userDto = this.userMapperImpl.toDTO(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteUserById(@PathVariable UUID id){
        var responseMessage = this.userService.deleteUser(id);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}

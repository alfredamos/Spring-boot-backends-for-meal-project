package com.alfredamos.meal_order.services;


import com.alfredamos.meal_order.controllers.OwnerCheck;
import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.entities.User;
import com.alfredamos.meal_order.exceptions.ForbiddenException;
import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.mapper.UserMapper;
import com.alfredamos.meal_order.repositories.UserRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapperImpl;
    private final OwnerCheck ownerCheck;

    //----> Delete a resource with given id.
    public ResponseMessage deleteUser(UUID id)  {
        //----> Check for ownership
        var isSameUser =  this.ownerCheck.compareAuthUserIdWithParamUserId(id);
        if (!isSameUser){
            throw new ForbiddenException("You are not permitted to view this resource!");
        }

        checkForOrderExistence(id); //----> Check for existence of user with the given id.

        //-----> Delete resource.
        userRepository.deleteById(id);

        return new ResponseMessage("Success", "User is deleted successfully!", 404);

    }

    //----> Get all users.
    public List<UserDto> getAllUsers(){
        var users = userRepository.findAll();

        return this.userMapperImpl.toDTOList(users);
    }

    //----> Get user by id.
    public UserDto getUserById(UUID id)  {
        //----> Check for ownership
        var isSameUser =  this.ownerCheck.compareAuthUserIdWithParamUserId(id);
        if (!isSameUser){
            throw new ForbiddenException("You are not permitted to view this resource!");
        }

        checkForOrderExistence(id); //----> Check for existence of user with the given id.

        //----> Get the user by id.
        var user =  this.userRepository.findById(id).orElse(null);

        return this.userMapperImpl.toDTO(user);
    }

    //----> Get user by email.
    public User getUserByEmail(String email)  {
        var exist = userRepository.existsUserByEmail(email);

        //----> Check for existence of user.
        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        //----> Get the user by id.
        return   this.userRepository.findUserByEmail(email);
    }


    private void checkForOrderExistence(UUID id){
        var exist = this.userRepository.existsById(id);

        //----> Check for existence of order.
        if (!exist){
            throw new NotFoundException("Order does not exist!");
        }
    }

}

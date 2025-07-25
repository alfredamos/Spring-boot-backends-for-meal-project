package com.alfredamos.meal_order.services;


import com.alfredamos.meal_order.entities.User;
import com.alfredamos.meal_order.repositories.UserRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    //----> Delete a resource with given id.
    public ResponseMessage deleteUser(UUID id)  {
        var exist = userRepository.existsById(id);

        //----> Check for existence of user.
        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        //-----> Delete resource.
        userRepository.deleteById(id);

        var responseMessage = new ResponseMessage("Success", "User is deleted successfully!", 404);

        return responseMessage;

    }

    //----> Get all users.
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //----> Get user by id.
    public Optional<User> getUserById(UUID id)  {
        var exist = userRepository.existsById(id);

        //----> Check for existence of user.
        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        //----> Get the user by id.
        return this.userRepository.findById(id);
    }

    //----> Get user by id.
    public User getUserByEmail(String email)  {
        var exist = userRepository.existsUserByEmail(email);

        //----> Check for existence of user.
        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        //----> Get the user by id.
        return userRepository.findUserByEmail(email);

    }


}

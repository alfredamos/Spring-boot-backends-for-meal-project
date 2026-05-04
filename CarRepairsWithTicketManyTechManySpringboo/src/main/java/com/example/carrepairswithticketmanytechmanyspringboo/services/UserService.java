package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.UserDto;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.UserMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDto deleteUserById(UUID id) {
        //----> Check for null user.
        this.finUserById(id);

        //----> Delete user.
        var deletedUser = userRepository.deleteUserById(id);

        //----> Send back response.
        return userMapper.toDTO(deletedUser);
    }

    @Override
    public UserDto getUserById(UUID id) {
        //----> Check for null user.
        var user = this.finUserById(id);

        //----> Send back response.
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        //----> Get all users.
        var users = userRepository.findAll();

        //----> Send back response.
        return userMapper.toDTOList(users);
    }

    @Override
    public String getUserEmail() {
        return this.getUserFromContext().getEmail();
    }

    @Override
    public Role getUserRole() {
        return this.getUserFromContext().getRole();
    }

    @Override
    public UUID getUserId() {
        return this.getUserFromContext().getId();
    }

    private User getUserFromContext(){
        //----> Get authentication.
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        //----> Get email.
        var email = (String) (authentication != null ? authentication.getPrincipal() : null);

        //----> Check for null email.
        if (email == null){
            throw  new NotFoundException("Current user is not found!");
        }

        //----> Get user from email.
        var user = userRepository.findUserByEmail(email);

        //----> Check for null user.
        if (user == null){
            throw  new NotFoundException("Current user is not found!");
        }

        //----> Return user.
        return user;
    }

    private User finUserById(UUID id){
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found!"));
    }
}

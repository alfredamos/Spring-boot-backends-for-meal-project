package com.example.carrepairshopspringbackend.services;

import com.example.carrepairshopspringbackend.dtos.UserDto;
import com.example.carrepairshopspringbackend.mapper.UserMapper;
import com.example.carrepairshopspringbackend.repositories.UserRepository;
import com.example.carrepairshopspringbackend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.example.carrepairshopspringbackend.utils.AuthParams.role;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService, UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        var user = this.userRepository.findUserByEmail(email);

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

    }

    public ResponseMessage deleteUserById(UUID id) {
        //----> Fetch user by id.
        var user = this.userRepository.getUsersById(id);

        //----> Delete user.
        this.userRepository.delete(user);

        return new ResponseMessage("User is deleted successfully", "success", HttpStatus.OK);
    }

    public com.example.carrepairshopspringbackend.entities.User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public UserDto getUserById(UUID id) {
        //-----> Fetch user by id.
        var user = this.userRepository.getUsersById(id);

        //-----> Send back response.
        return userMapper.toDTO(user);
    }

    public List<UserDto> getAllUsers(){
        //----> Fetch all users.
        return userMapper.toDTOList(userRepository.findAll());
    }

}


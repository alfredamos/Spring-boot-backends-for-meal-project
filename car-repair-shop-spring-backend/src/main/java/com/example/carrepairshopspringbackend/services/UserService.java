package com.example.carrepairshopspringbackend.services;

import com.example.carrepairshopspringbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        var user = this.userRepository.findUserByEmail(email);

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

    }

    public com.example.carrepairshopspringbackend.entities.User getUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public com.example.carrepairshopspringbackend.entities.User getUserById(UUID id) {
        return this.userRepository.getUsersById(id);
    }

    public List<com.example.carrepairshopspringbackend.entities.User> getAllUsers(){
        return userRepository.findAll();
    }

}


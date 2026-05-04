package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.repositories.AuthRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {
    private final AuthRepository authRepository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        var user = authRepository.findByEmail(email);

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

}

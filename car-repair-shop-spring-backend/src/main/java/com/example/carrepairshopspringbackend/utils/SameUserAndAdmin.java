package com.example.carrepairshopspringbackend.utils;

import com.example.carrepairshopspringbackend.entities.Role;
import com.example.carrepairshopspringbackend.entities.User;
import com.example.carrepairshopspringbackend.exceptions.ForbiddenException;
import com.example.carrepairshopspringbackend.exceptions.NotFoundException;
import com.example.carrepairshopspringbackend.mapper.UserMapper;
import com.example.carrepairshopspringbackend.repositories.AuthRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class SameUserAndAdmin {
    private final AuthRepository authRepository;
    private final UserMapper userMapper;

    public void checkForOwnerShipOrAdmin(UUID userId){
        var currentUserInfo = currentUserInfo(); //----> Current-user info.

        //----> Compare the giving user-id with the current-user id.
        var isSameUser = userId.equals(currentUserInfo.getUser().getId());
        var isAdmin = Role.Admin.equals(currentUserInfo.getRole());

        //----> Check for different-user and not admin
        if (!isSameUser && !isAdmin){
            throw new ForbiddenException("You are not permitted to view or perform this operation");
        }

    }

    public boolean checkForAdmin(){
        var currentUserInfo = currentUserInfo(); //----> Current-user info.

        //----> Check for admin privilege.
        return Role.Admin.equals(currentUserInfo.getRole());
    }

    private UserAndAdmin currentUserInfo(){
        User authUser = getUserFromContext(); //----> Current user.

        if  (authUser == null) {
            throw new NotFoundException("User not found!");
        }

        return new UserAndAdmin(authUser.getRole(), authUser);
    }

    public User getUserFromContext(){
        //----> Get authentication.
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        //----> Get email.
        var email = (String) (authentication != null ? authentication.getPrincipal() : null);

        //----> Check for null email.
        if (email == null){
            throw  new NotFoundException("Current user is not found!");
        }

        //----> Get user from email.
        var user = authRepository.findUserByEmail(email);

        //----> Check for null user.
        if (user == null){
            throw  new NotFoundException("Current user is not found!");
        }

        //----> Return user.
        return user;
    }

}

package com.example.carrepairshopspringbackend.configs;

import com.example.carrepairshopspringbackend.entities.Role;
import com.example.carrepairshopspringbackend.exceptions.ForbiddenException;
import com.example.carrepairshopspringbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("sameUserCheck")
@RequiredArgsConstructor
public class SameUserCheck {
    private final UserService userService;

    public boolean isOwnerCheckByEmail(String email) {
       //----> Get email from context.
        var emailFromContext = userService.getEmail();

        System.out.println("In same-user-check, contextEmail : " + emailFromContext + " userEmail : " + email);

        //----> Check for equality
        return emailFromContext.equals(email);

    }

    public boolean isOwnerCheckById(UUID id) {
        //----> Get User id.
        var userId = userService.getId();

        System.out.println("In same-user-check, contextUserId : " + userId + " userId : " + id);

        //----> Check for equality
        var isEqual = userId.equals(id);

        //----> Check for not equal.
        if(!isEqual){
            throw new ForbiddenException("You are not permitted to view or perform this operation");
        }

        //----> Return true.
        return true;
    }

    public boolean isAdmin(){
        var isAdmin = userService.getRole().equals(Role.Admin);

        //----> Check for not admin.
        if(!isAdmin){
            throw new ForbiddenException("You are not permitted to perform this operation");
        }

        //----> Return true.
        return true;
    }


}

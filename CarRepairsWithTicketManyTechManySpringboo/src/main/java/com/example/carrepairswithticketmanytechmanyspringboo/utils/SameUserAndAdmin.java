package com.example.carrepairswithticketmanytechmanyspringboo.utils;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Role;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Technician;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.ForbiddenException;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.UserMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.AuthRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.TechnicianRepository;
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
    private final TechnicianRepository technicianRepository;
    private final UserMapper userMapper;

    public void checkForOwnerOrAdminByUserId(UUID userId){
        var currentUserInfo = currentUserInfo(); //----> Current-user info.

        //----> Compare the giving user-id with the current-user id.
        var isSameUser = userId.equals(currentUserInfo.getUser().getId());
        var isAdmin = Role.Admin.equals(currentUserInfo.getRole());

        //----> Check for different-user and not admin
        if (!isSameUser && !isAdmin){
            throw new ForbiddenException("You are not permitted to view or perform this operation");
        }

    }

    public void checkForOwnerOrAdminByTechId(UUID techId){
        var currentUserInfo = currentUserInfo(); //----> Current-user info.

        //----> Get user-id from tech-id and current-user id.
        var userIdFromTech = currentTechnicianInfo(techId).getUser().getId();
        var userIdFromAuth = currentUserInfo().getUser().getId();

        //----> Check for same-user.
        var isSameUser = userIdFromAuth.equals(userIdFromTech);

        //----> Check for admin privilege.
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

    private UserAndAdmin currentUserInfo(){
        User authUser = getUserFromContext(); //----> Current user.

        if  (authUser == null) {
            throw new NotFoundException("User not found!");
        }

        return new UserAndAdmin(authUser.getRole(), authUser);
    }

    private Technician currentTechnicianInfo(UUID techId){
        //----> Get technician by id.
        var tech = technicianRepository.getTechnicianById(techId);

        //----> Check for null technician.
        if (tech == null){
            throw new NotFoundException("Technician not found!");
        }

        //----> Return technician.
        return tech;
    }

}

package com.alfredamos.meal_order.controllers;

import com.alfredamos.meal_order.entities.Role;
import com.alfredamos.meal_order.entities.User;
import com.alfredamos.meal_order.repositories.OrderRepository;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@AllArgsConstructor
public class OwnerCheck {
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final UserService userService;

    public boolean compareUserId(UUID userId){
        //----> Get the user id from security context.
        var idOfUser = getUserIdFromContext();

        //----> Compare the two user id for equality.
        return idOfUser.equals(userId);

    }

    public OrderBySameUser compareUserIdForOrder(UUID orderId){
        //----> Get the user id from security context.
        var idOfUser = getUserIdFromContext();

        //----> Get the order.
        var order = orderRepository.findById(orderId).orElseThrow();
        var userId = order.getUser().getId(); //----> Get the id of the user that made the order.

        //----> Compare the two user id for equality.
        var isOwner = idOfUser.equals(userId);

        //----> Map order to orderDto
        var orderDto = orderService.attachCartItemsDtoToOrderDto(order);

        return new OrderBySameUser(orderDto, isOwner);

    }

    public boolean isAdminUser(){
        //----> Check for admin role.
        return getCurrentUser().getRole().equals(Role.Admin);
    }

    private UUID getUserIdFromContext(){
       //----> Get user id.
        return getCurrentUser().getId();
    }

    private User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) authentication.getPrincipal();
        return userService.getUserByEmail(email);
    }
}

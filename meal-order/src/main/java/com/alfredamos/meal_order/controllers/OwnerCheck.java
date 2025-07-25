package com.alfredamos.meal_order.controllers;

import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.mapper.OrderMapper;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@AllArgsConstructor
public class OwnerCheck {
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final UserService userService;

    public boolean compareUserId(UUID userId){
        //----> Get the user id from security context.
        var idOfUser = getUserIdFromContext();

    //----> Compare the two user id for equality.
        return idOfUser.equals(userId);

    }

    public boolean compareUserIdForOrder(UUID orderId){
        //----> Get the user id from security context.
        var idOfUser = getUserIdFromContext();

        //----> Get the order.
        var order = orderMapper.toEntity(orderService.getOrderById(orderId));
        var userId = order.getUser().getId(); //----> Get the id of the user that made the order.

        //----> Compare the two user id for equality.
        return idOfUser.equals(userId);

    }

    private UUID getUserIdFromContext(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) authentication.getPrincipal();
        var user = userService.getUserByEmail(email);
        return user.getId();
    }
}

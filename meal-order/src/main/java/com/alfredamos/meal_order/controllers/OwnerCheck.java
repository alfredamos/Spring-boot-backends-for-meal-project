package com.alfredamos.meal_order.controllers;

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

        System.out.println("****************************************************");
        System.out.println("In compare-userId, userId : " + userId);
        System.out.println("In compare-userId, idOfUser : " + idOfUser);
        System.out.println("****************************************************");

    //----> Compare the two user id for equality.
        return idOfUser.equals(userId);

    }

    public OrderBySameUser compareUserIdForOrder(UUID orderId){
        //----> Get the user id from security context.
        var idOfUser = getUserIdFromContext();

        //----> Get the order.
        var order = orderRepository.findById(orderId).orElseThrow();
        var userId = order.getUser().getId(); //----> Get the id of the user that made the order.

        System.out.println("In compare-method, idOfUser : " + idOfUser);
        System.out.println("In compare-method, userId : " + userId);
        //----> Compare the two user id for equality.
        var isOwner = idOfUser.equals(userId);

        //----> Map order to orderDto
        var orderDto = orderService.attachCartItemsDtoToOrderDto(order);

        return new OrderBySameUser(orderDto, isOwner);

    }

    private UUID getUserIdFromContext(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) authentication.getPrincipal();
        var user = userService.getUserByEmail(email);
        return user.getId();
    }
}

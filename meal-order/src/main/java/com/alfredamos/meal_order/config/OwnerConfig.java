package com.alfredamos.meal_order.config;

import com.alfredamos.meal_order.controllers.OwnerCheck;
import com.alfredamos.meal_order.repositories.OrderRepository;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class OwnerConfig {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private final UserService userService;

    @Bean
    public OwnerCheck ownerCheck(){
        return new OwnerCheck(orderRepository, orderService, userService);
    }
}

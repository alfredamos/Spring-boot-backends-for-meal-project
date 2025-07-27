package com.alfredamos.meal_order.controllers;

import com.alfredamos.meal_order.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderBySameUser {
    private OrderDto orderDto;
    private boolean isOwner;
}

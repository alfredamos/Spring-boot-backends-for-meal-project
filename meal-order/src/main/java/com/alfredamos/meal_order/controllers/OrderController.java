package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.exceptions.ForbiddenException;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Tag(name = "orders")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OwnerCheck ownerCheck;

    @PatchMapping("/checkout")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        var newOrderDto = this.orderService.createOrder(orderDto);

        return new ResponseEntity<>(newOrderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteOrderById(@PathVariable UUID id){
        //----> Check for ownership.
        var isOwner = ownerCheck.compareUserIdForOrder(id);
        if (!isOwner){
            throw new ForbiddenException("You are not permitted to delete this order!");
        }

        var result = this.orderService.deleteOrderById(id);

    return ResponseEntity.ok(result);

    }

    @DeleteMapping("/delete-all-orders-by-user-id/{userId}")
    public ResponseEntity<ResponseMessage> deleteOrdersByUserId(@PathVariable UUID userId){
        var isSameUser = ownerCheck.compareUserId(userId);
        if (!isSameUser){
            throw new ForbiddenException("You are not permitted to view this resource!");
        }

        var result = this.orderService.deleteOrdersByUser(userId);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> editOrderById(@PathVariable UUID id){
        //----> Check for ownership.
        var isOwner = ownerCheck.compareUserIdForOrder(id);
        if (!isOwner){
            throw new ForbiddenException("You are not permitted to delete this order!");
        }

       var orderDto = this.orderService.editOrderById(id);

       return ResponseEntity.ok(orderDto);

    }

    @GetMapping("/orders-by-user-id/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable UUID userId){
        var isSameUser = ownerCheck.compareUserId(userId);
        if (!isSameUser){
            throw new ForbiddenException("You are not permitted to view this resource!");
        }

        var ordersDto = this.orderService.getAllOrdersByUser(userId);

        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id){
        //----> Check for ownership.
        var isOwner = ownerCheck.compareUserIdForOrder(id);
        if (!isOwner){
            throw new ForbiddenException("You are not permitted to delete this order!");
        }

        var orderDto = this.orderService.getOrderById(id);

        return ResponseEntity.ok(orderDto);
    }

}

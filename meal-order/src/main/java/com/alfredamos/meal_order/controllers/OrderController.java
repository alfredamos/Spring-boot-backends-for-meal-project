package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.mapper.OrderMapper;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final UserService userService;
    private final OrderMapper orderMapper;

    @PatchMapping("/checkout")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {

        var newOrder = this.orderService.createOrder(orderDto);

        var newOrderDto = this.orderMapper.toDTO(newOrder);

        return new ResponseEntity<>(newOrderDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteOrderById(@PathVariable UUID id){
    var result = this.orderService.deleteOrderById(id);

    return ResponseEntity.ok(result);

    }

    @DeleteMapping("/delete-all-orders-by-user-id/{userId}")
    public ResponseEntity<ResponseMessage> deleteOrdersByUserId(@PathVariable UUID userId){

        var result = this.orderService.deleteOrdersByUser(userId);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> editOrderById(@PathVariable UUID id){
       var order = this.orderService.editOrderById(id);

       var orderDto = this.orderMapper.toDTO(order);

       return ResponseEntity.ok(orderDto);

    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        var orders = this.orderService.getAllOrders();

        var ordersDto = this.orderMapper.toDTOList(orders);

        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("/orders-by-user-id/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable UUID userId){
        //----> Get the user.
        var user = this.userService.getUserById(userId).orElse(null);

        var orders = this.orderService.getAllOrdersByUser(user);

        var ordersDto = this.orderMapper.toDTOList(orders);

        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id){
        var order = this.orderService.getOrderById(id).orElse(null);

        var orderDto = this.orderMapper.toDTO(order);

        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/delivered/{id}")
    public ResponseEntity<OrderDto> orderDelivered(@PathVariable UUID id){
        var order = this.orderService.deliveredOrder(id);

        var orderDto = this.orderMapper.toDTO(order);

        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/shipped/{id}")
    public ResponseEntity<OrderDto> orderShipped(@PathVariable UUID id){
        var order = this.orderService.shippedOrder(id);

        var orderDto = this.orderMapper.toDTO(order);

        return ResponseEntity.ok(orderDto);
    }
}

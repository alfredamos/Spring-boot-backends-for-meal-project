package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.dto.PizzaDto;
import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.PizzaService;
import com.alfredamos.meal_order.services.UserService;
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
@RestController
@Tag(name = "admin")
@RequestMapping("/api/admin")
public class AdminController {
    private OrderService orderService;
    private PizzaService pizzaService;
    private UserService userService;

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ResponseMessage> deleteOrderById(@PathVariable UUID id){
        var result = this.orderService.deleteOrderById(id);

        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/orders/delete-all-orders")
    public ResponseEntity<ResponseMessage> deleteAllOrders(){
        var result = this.orderService.deleteAllOrders();

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/orders/delete-all-orders-by-user-id/{userId}")
    public ResponseEntity<ResponseMessage> deleteOrdersByUserId(@PathVariable UUID userId){
        var result = this.orderService.deleteOrdersByUser(userId);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<OrderDto> editOrderById(@PathVariable UUID id){
        var orderDto = this.orderService.editOrderById(id);

        return ResponseEntity.ok(orderDto);

    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        var ordersDto = this.orderService.getAllOrders();

        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("orders/orders-by-user-id/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable UUID userId){
        var ordersDto = this.orderService.getAllOrdersByUser(userId);

        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id){
        var orderDto = this.orderService.getOrderById(id);

        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/orders/delivered/{id}")
    public ResponseEntity<OrderDto> orderDelivered(@PathVariable UUID id){
        var orderDto = this.orderService.deliveredOrder(id);

        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/orders/shipped/{id}")
    public ResponseEntity<OrderDto> orderShipped(@PathVariable UUID id){
        var orderDto = this.orderService.shippedOrder(id);

        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/pizzas")
    public ResponseEntity<PizzaDto> createPizza(@Valid @RequestBody PizzaDto pizzaDto){
        this.pizzaService.createPizza(pizzaDto);

        return  new ResponseEntity<>(pizzaDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/pizzas/{id}")
    public ResponseEntity<ResponseMessage> deletePizza(@PathVariable UUID id){
        var result = this.pizzaService.deletePizza(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/pizzas/{id}")
    public ResponseEntity<PizzaDto> editPizza(@PathVariable UUID id, @Valid @RequestBody PizzaDto pizzaDto){
        this.pizzaService.editPizza(id, pizzaDto);

        return new ResponseEntity<>(pizzaDto, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        var usersDto = this.userService.getAllUsers();

        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id){
        var userDto = this.userService.getUserById(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseMessage> deleteUserById(@PathVariable UUID id){
        var responseMessage = this.userService.deleteUser(id);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}

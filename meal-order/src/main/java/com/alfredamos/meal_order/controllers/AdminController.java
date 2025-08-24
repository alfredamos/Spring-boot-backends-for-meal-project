package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.dto.PizzaDto;
import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.PizzaService;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final OrderService orderService;
    private final OwnerCheck  ownerCheck;
    private final UserService userService;
    private final PizzaService pizzaService;

    //----> Pizzas ***************************************************************************
    @PostMapping("/pizzas")
    public ResponseEntity<PizzaDto> createPizza(@Valid @RequestBody PizzaDto pizzaDto) {
        //----> Create a new pizza.
        var response = pizzaService.createPizza(pizzaDto);

        //----> Send back the response.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/pizzas/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable(name = "id") UUID id) {
        System.out.println("Deleting Menu Item, id: " + id);
        //----> Delete the menu with the given id.
        var response = pizzaService.deletePizza(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/pizzas/{id}")
    public ResponseEntity<?> editMenu(@PathVariable(name = "id") UUID id, @Valid @RequestBody PizzaDto pizzaDto) {
        //----> Update the menu with the given id.
        var response = pizzaService.editPizza(id, pizzaDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pizzas")
    public ResponseEntity<List<PizzaDto>> findAllMenus() {
        //----> Retrieve all the pizzas.
        var response = pizzaService.getAllPizzas();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/pizzas/{id}")
    public ResponseEntity<PizzaDto> findMenuById(@PathVariable(name = "id") UUID id) {
        //----> Retrieve all the pizzas.
        var response = pizzaService.getPizzaById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //----> *********************************************************************************

    //----> Orders @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    @DeleteMapping("/orders/delete-all-orders")
    public ResponseEntity<ResponseMessage> deleteAllOrders() {
        //----> Delete all orders.
        var orders = orderService.deleteAllOrders();

        //----> Send back the response.
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ResponseMessage> deleteOrderById(@PathVariable(name = "id") UUID id){
        //----> Check for existence of order.
        orderService.getOneOrder(id);

        //----> Delete the order with given id.
        var response = orderService.deleteOrderById(id, ownerCheck.isAdminUser());

        //----> send back the response.
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/orders/delivered/{id}")
    public ResponseEntity<OrderDto> deliveredOrderById(@PathVariable(name = "id") UUID id){
        //----> Mark the order with the given id delivered.
        var order = orderService.deliveredOrder(id);

        //----> Send back the response.
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/orders/delete-all-orders-by-user-id/{userId}")
    public ResponseEntity<ResponseMessage> deleteOrdersByUserId(@PathVariable(name = "userId") UUID userId){
        //----> Delete the order with given id.
        var response = orderService.deleteOrdersByUserId(userId, ownerCheck.isAdminUser());

        //----> send back the response.
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        //----> Get all orders.
        var orders = orderService.getAllOrders();

        //----> Send back the response.
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable(name = "id") UUID id){
        //----> Check for existence of order.
        orderService.getOneOrder(id);

        //----> Delete the order with given id.
        var response = orderService.getOrderById(id, ownerCheck.isAdminUser());

        //----> send back the response.
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/orders/orders-by-user-id/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable(name = "userId") UUID userId){
        //----> Delete the order with given id.
        var response = orderService.getAllOrdersByUserById(userId, ownerCheck.isAdminUser());

        //----> send back the response.
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/orders/shipped/{id}")
    public ResponseEntity<OrderDto> shippedOrderById(@PathVariable(name = "id") UUID id){
        //----> Mark the order with the given id shipped.
        var order = orderService.shippedOrder(id);

        //----> Send back the response.
        return ResponseEntity.ok(order);
    }

    //----> @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //----> Users ############################################################################
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseMessage> deleteUserById(@PathVariable(name = "id") UUID id){
        //----> Delete the user with the given id.
        var response = userService.deleteUserById(id, ownerCheck.isAdminUser());

        //----> Send back the response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        //----> Delete the user with the given id.
        var response = userService.getAllUsers();

        //----> Send back the response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") UUID id){
        //----> Delete the user with the given id.
        var response = userService.getUserById(id, ownerCheck.isAdminUser());

        //----> Send back the response.
        return ResponseEntity.ok(response);
    }

    //----> ################################################################################

}

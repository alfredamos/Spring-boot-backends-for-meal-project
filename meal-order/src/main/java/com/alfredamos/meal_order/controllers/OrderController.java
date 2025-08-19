package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.services.CheckoutSession;
import com.alfredamos.meal_order.services.OrderService;
import com.alfredamos.meal_order.services.WebhookRequest;
import com.alfredamos.meal_order.utils.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutSession> createOrder(@Valid @RequestBody OrderDto orderDto) {
        var checkoutSession = this.orderService.createOrder(orderDto);

        return new ResponseEntity<>(checkoutSession, HttpStatus.CREATED);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestHeader Map<String, String> headers, @RequestBody String payload) {
        orderService.handleWebhook(new WebhookRequest(headers, payload));

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteOrderById(@PathVariable UUID id){
    //----> Delete the order.
    var result = this.orderService.deleteOrderById(id);

    //----> Send back the response4.
    return ResponseEntity.ok(result);

    }

    @DeleteMapping("/delete-all-orders-by-user-id/{userId}")
    public ResponseEntity<ResponseMessage> deleteOrdersByUserId(@PathVariable UUID userId){
        //----> Delete all orders attach to the user with the given id.
        var result = this.orderService.deleteOrdersByUser(userId);

        //----> Send back the response.
        return ResponseEntity.ok(result);
    }


    @GetMapping("/orders-by-user-id/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUserId(@PathVariable UUID userId){
        //----> Get all orders attach with user-id.
        var ordersDto = this.orderService.getAllOrdersByUser(userId);

        //----> Send back the response.
        return ResponseEntity.ok(ordersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id){
        //----> Get the order
        var orderDto = orderService.getOrderById(id);

        return ResponseEntity.ok(orderDto);
    }

}

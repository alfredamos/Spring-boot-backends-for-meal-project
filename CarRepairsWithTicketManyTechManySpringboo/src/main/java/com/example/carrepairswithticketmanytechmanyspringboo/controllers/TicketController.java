package com.example.carrepairswithticketmanytechmanyspringboo.controllers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketCreate request){
        var response = ticketService.createTicket(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable UUID id){
        var response = ticketService.deleteTicketById(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editTicketById(@PathVariable UUID id, @Valid @RequestBody TicketEdit request){
        var response = ticketService.editTicketById(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketId(@PathVariable UUID id){
        var response = ticketService.getTicketById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllTickets(){
        var response = ticketService.getAllTickets();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public ResponseEntity<?> getTicketsByCustomerId(@PathVariable UUID customerId){
        var response = ticketService.getTicketsByCustomerId(customerId);

        return ResponseEntity.ok(response);
    }
}

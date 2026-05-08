package com.example.carrepairswithticketmanytechmanyspringboo.controllers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.services.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketCreate request){
        var response = ticketService.createTicket(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> deleteTicketById(@PathVariable UUID id){
        var response = ticketService.deleteTicketById(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> editTicketById(@PathVariable UUID id, @Valid @RequestBody TicketEdit request){
        var response = ticketService.editTicketById(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getTicketId(@PathVariable UUID id){
        var response = ticketService.getTicketById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getAllTickets(){
        var response = ticketService.getAllTickets();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-customer-id/{customerId}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getTicketsByCustomerId(@PathVariable UUID customerId){
        var response = ticketService.getTicketsByCustomerId(customerId);

        return ResponseEntity.ok(response);
    }
}

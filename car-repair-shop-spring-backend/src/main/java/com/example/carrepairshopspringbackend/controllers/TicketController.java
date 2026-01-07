package com.example.carrepairshopspringbackend.controllers;

import com.example.carrepairshopspringbackend.dtos.TicketCreate;
import com.example.carrepairshopspringbackend.dtos.TicketDto;
import com.example.carrepairshopspringbackend.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@RestController
public class TicketController {
    private final TicketService ticketService;

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<?> changeTicketStatus(@PathVariable UUID id){
        //----> Change the ticket status.
        var response = ticketService.changeTicketStatus(id);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> createTicket(@RequestBody TicketCreate request){
        //----> Create a new ticket.
        var response = ticketService.CreateTicket(request);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteTicketById(@PathVariable UUID id){
        //----> Delete ticket by id.
        var response = ticketService.deleteTicketById(id);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> editTicketById(@PathVariable UUID id, @RequestBody TicketDto request){
        //----> Edit ticket by id.
        var response = ticketService.editTicketById(id, request);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable UUID id){
        //----> Fetch ticket by id.
        var response = ticketService.getTicketById(id);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllTickets(){
        //----> Fetch all tickets.
        var response = ticketService.getAllTickets();

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/get-all-complete-tickets")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getCompleteTickets(){
        //----> Fetch completed tickets.
        var response = ticketService.getCompleteTickets();

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/get-all-incomplete-tickets")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getIncompleteTickets(){
        //----> Fetch incomplete tickets.
        var response = ticketService.getIncompleteTickets();

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-tickets-by-user-email/{email}")
    public ResponseEntity<?> getTicketsByEmail(@PathVariable String email){
        //----> Fetch tickets by email.
        var response = ticketService.getTicketsByEmail(email);

        //----> Return response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-tickets-by-customer-id/{customerId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getTicketsByCustomerId(@PathVariable UUID customerId){
        //----> Fetch tickets by email.
        var response = ticketService.getTicketsByCustomerId(customerId);

        //----> Return response.
        return ResponseEntity.ok(response);
    }


}

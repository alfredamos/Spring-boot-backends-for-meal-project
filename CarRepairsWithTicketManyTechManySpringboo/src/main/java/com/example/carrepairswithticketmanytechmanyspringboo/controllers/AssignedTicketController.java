package com.example.carrepairswithticketmanytechmanyspringboo.controllers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.services.IAssignedTicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assign-tickets")
public class AssignedTicketController {
    private final IAssignedTicketService assignedTicketService;

    @PostMapping
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> createAssignedTicket(@Valid @RequestBody AssignedTicketCreate ticketCreate, HttpServletRequest request){
        var response = assignedTicketService.createAssignedTicket(ticketCreate, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/change-status/{techId}/{ticketId}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByTechId(#techId)")
    public ResponseEntity<?> changeAssignedTicketStatus(@PathVariable UUID techId, @PathVariable UUID ticketId){
        var response = assignedTicketService.changeAssignedTicketStatus(techId, ticketId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{techId}/{ticketId}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> deleteAssignedTicketById(@PathVariable UUID techId, @PathVariable UUID ticketId){
        var response = assignedTicketService.deleteAssignedTicketById(techId, ticketId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{techId}/{ticketId}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> editAssignedTicketById(@PathVariable UUID techId, @PathVariable UUID ticketId, @Valid @RequestBody AssignedTicketEdit request){
        var response = assignedTicketService.editAssignedTicketById(techId, ticketId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{techId}/{ticketId}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByTechId(#techId)")
    public ResponseEntity<?> getAssignedTicketById(@PathVariable UUID techId, @PathVariable UUID ticketId){
        var response = assignedTicketService.getAssignedTicketById(techId, ticketId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getAllAssignedTickets(){
        var response = assignedTicketService.getAllAssignedTicket();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-tech-id/{techId}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByTechId(#techId)")
    public ResponseEntity<?> getAssignedTicketByTechId(@PathVariable UUID techId){
        var response = assignedTicketService.getAssignedTicketsByTechId(techId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-ticket-id/{ticketId}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getAssignedTicketByTicketId(@PathVariable UUID ticketId){
        var response = assignedTicketService.getAssignedTicketsByTicketId(ticketId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/completed")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getCompletedAssignedTickets(){
        var response = assignedTicketService.getCompletedAssignedTicket();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/incompleted")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getIncompletedAssignedTickets(){
        var response = assignedTicketService.getInCompletedAssignedTicket();

        return ResponseEntity.ok(response);
    }

}

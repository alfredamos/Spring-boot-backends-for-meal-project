package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface IAssignedTicketService {
    AssignedTicketResponse changeAssignedTicketStatus(UUID techId, UUID ticketId);
    AssignedTicketResponse createAssignedTicket(AssignedTicketCreate ticket, HttpServletRequest request);
    AssignedTicketResponse deleteAssignedTicketById(UUID techId, UUID ticketId);
    AssignedTicketResponse editAssignedTicketById(UUID techId, UUID ticketId, AssignedTicketEdit request);
    AssignedTicketResponse getAssignedTicketById(UUID techId, UUID ticketId);
    List<AssignedTicketResponse> getAllAssignedTicket();
    List<AssignedTicketResponse> getAssignedTicketsByTechId(UUID techId);
    List<AssignedTicketResponse> getAssignedTicketsByTicketId(UUID ticketId);
    List<AssignedTicketResponse> getCompletedAssignedTicket();
    List<AssignedTicketResponse> getInCompletedAssignedTicket();
}

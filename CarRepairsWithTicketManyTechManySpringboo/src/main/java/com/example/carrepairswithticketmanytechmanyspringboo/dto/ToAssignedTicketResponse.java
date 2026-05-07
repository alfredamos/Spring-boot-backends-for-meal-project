package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.AssignedTicket;

public class ToAssignedTicketResponse {
    static public AssignedTicketResponse toAssignedTicketResponse(AssignedTicket ticket){
        return AssignedTicketResponse.builder()
                .assignAt(ticket.getAssignAt())
                .assignBy(ticket.getAssignBy())
                .completed(ticket.getCompleted())
                .customerAddress(ticket.getTicket().getCustomer().getAddress())
                .customerEmail(ticket.getTicket().getCustomer().getUser().getEmail())
                .customerImage(ticket.getTicket().getCustomer().getUser().getImage())
                .customerName(ticket.getTicket().getCustomer().getUser().getName())
                .customerPhone(ticket.getTicket().getCustomer().getUser().getPhone())
                .techId(ticket.getId().getTechId())
                .ticketId(ticket.getId().getTicketId())
                .techSpecialty(ticket.getTech().getSpecialty())
                .techPhone(ticket.getTech().getUser().getPhone())
                .techImage(ticket.getTech().getUser().getImage())
                .techEmail(ticket.getTech().getUser().getEmail())
                .techName(ticket.getTech().getUser().getName())
                .ticketNotes(ticket.getTicket().getDescription())
                .ticketTitle(ticket.getTicket().getTitle())
                .status(ticket.getStatus())
                .build();
    }
}

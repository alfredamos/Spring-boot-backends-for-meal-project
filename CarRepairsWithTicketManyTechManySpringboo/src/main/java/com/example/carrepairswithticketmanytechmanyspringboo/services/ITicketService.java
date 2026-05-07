package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketResponse;

import java.util.List;
import java.util.UUID;

public interface ITicketService{
    TicketResponse createTicket(TicketCreate request);
    TicketResponse deleteTicketById(UUID id);
    TicketResponse editTicketById(UUID id, TicketEdit request);
    TicketResponse getTicketById(UUID id);
    List<TicketResponse> getTicketsByCustomerId(UUID customerId);
    List<TicketResponse> getAllTickets();
}

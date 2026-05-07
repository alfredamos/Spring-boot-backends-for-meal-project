package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.ToTicketResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Ticket;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.TicketMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.CustomerRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService{
    private final CustomerRepository customerRepository;
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse createTicket(TicketCreate request) {
        //----> Get the customer associated with the ticket.
        var customer = customerRepository.getCustomerById(request.getCustomerId());

        //----> Map Ticket-create to ticket.
        var ticket = ticketMapper.toEntity(request);
        ticket.setCustomer(customer);

        //----> Save the ticket.
        var savedTicket = ticketRepository.save(ticket);

        //----> Return the ticket.
        return ToTicketResponse.toTicketResponse(savedTicket);
    }

    @Transactional
    @Override
    public TicketResponse deleteTicketById(UUID id) {
        //----> Check for null ticket.
        this.getOneTicket(id);

        //----> Delete the ticket.
        var deletedTicket = ticketRepository.deleteTicketById(id);

        //----> Send back response.
        return ToTicketResponse.toTicketResponse(deletedTicket);
    }

    @Override
    public TicketResponse editTicketById(UUID id, TicketEdit request) {
        //----> Get the customer associated with the ticket.
        var customer = customerRepository.getCustomerById(request.getCustomerId());

        //----> Check for null ticket.
        this.getOneTicket(id);

        //----> Map Ticket-edit to ticket.
        var ticket = ticketMapper.toEntity(request);
        ticket.setId(id);
        ticket.setCustomer(customer);

        //----> Save the ticket.
        var editedTicket = ticketRepository.save(ticket);

        //----> Return the ticket.
        return ToTicketResponse.toTicketResponse(editedTicket);

    }

    @Override
    public TicketResponse getTicketById(UUID id) {
        //----> Get the ticket by id.
        var ticket = this.getOneTicket(id);

        //----> Return the ticket.
        return ToTicketResponse.toTicketResponse(ticket);
    }

    @Override
    public List<TicketResponse> getTicketsByCustomerId(UUID customerId) {
        //----> Fetch tickets by customer-id.
        var tickets = ticketRepository.findTicketsByCustomerId(customerId);

        //----> Return the tickets.
        return tickets.stream().map(ToTicketResponse::toTicketResponse).toList();
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        //----> Fetch all tickets.
        var tickets = ticketRepository.findAll();

        //----> Return the tickets.
        return tickets.stream().map(ToTicketResponse::toTicketResponse).toList();
    }

    private Ticket getOneTicket(UUID id){
        return ticketRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found in db!"));
    }
}

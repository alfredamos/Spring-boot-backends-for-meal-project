package com.example.carrepairshopspringbackend.services;

import com.example.carrepairshopspringbackend.dtos.TicketCreate;
import com.example.carrepairshopspringbackend.dtos.TicketDto;
import com.example.carrepairshopspringbackend.entities.Ticket;
import com.example.carrepairshopspringbackend.mapper.TicketMapper;
import com.example.carrepairshopspringbackend.repositories.TicketRepository;
import com.example.carrepairshopspringbackend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketService {
    private final TicketMapper ticketMapper;
    private final TicketRepository ticketRepository;

    public ResponseMessage changeTicketStatus(UUID id){
        //----> Retrieve ticket by id.
        var ticket = getOneTicket(id);

        //----> Change the ticket status.
        ticket.setCompleted(!ticket.getCompleted());
        ticket.setId(id);

        //----> Update ticket in db.
        ticketRepository.save(ticket);

        //----> Send back response.
        return new ResponseMessage("Ticket status has been changed successfully!", "status", HttpStatus.OK);
    }

    public TicketDto CreateTicket(TicketCreate request){
        //----> Map ticket dto to ticket entity.
        var ticketToCreate = ticketMapper.toEntity(request);

        //----> Save the ticket in db and return it.
        return ticketMapper.toDTO(ticketRepository.save(ticketToCreate));

    }

    public ResponseMessage deleteTicketById(UUID id){
        //----> Retrieve ticket by id.
        getOneTicket(id);

        //----> Delete ticket.
        ticketRepository.deleteById(id);

        //----> Send back response.
        return new ResponseMessage("Ticket has been deleted successfully!", "success", HttpStatus.OK);
    }

    public ResponseMessage editTicketById(UUID id, TicketDto ticketDto){
        //----> Map ticket dto to ticket entity.
        var ticketToEdit = ticketMapper.toEntity(ticketDto);

        //----> Retrieve ticket by id.
        getOneTicket(id);
        ticketToEdit.setId(id);

        //----> Edit ticket.
        ticketRepository.save(ticketToEdit);

        //----> Send back response.
        return new ResponseMessage("Ticket has been deleted successfully!", "success", HttpStatus.OK);
    }

    public TicketDto getTicketById(UUID id){
        //----> Retrieve ticket by id.
        return ticketMapper.toDTO(getOneTicket(id));
    }

    public List<TicketDto> getAllTickets(){
        //----> Retrieve all tickets.
        return ticketMapper.toDTOList(ticketRepository.findAll());
    }

    public List<TicketDto> getCompleteTickets(){
        //----> Retrieve all complete tickets.
        return ticketMapper.toDTOList(ticketRepository.getCompletedTickets());
    }

    public List<TicketDto> getIncompleteTickets(){
        //----> Retrieve all incomplete tickets.
        return ticketMapper.toDTOList(ticketRepository.getIncompleteTickets());
    }

    public List<TicketDto> getTicketsByEmail(String email){
        //----> Retrieve all tickets by email.
        return ticketMapper.toDTOList(ticketRepository.findTicketsByTech(email));
    }

    public List<TicketDto> getTicketsByCustomerId(UUID customerId){
        //----> Retrieve all tickets by customer id.
        return ticketMapper.toDTOList(ticketRepository.findTicketsByCustomerId(customerId));
    }

    private Ticket getOneTicket(UUID id){
        //----> Retrieve ticket by id.
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found!"));
    }
}

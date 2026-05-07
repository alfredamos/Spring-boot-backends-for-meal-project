package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.ToAssignedTicketResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.AssignedTicket;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Status;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.AssignedTicketMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.AssignedTicketRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.TechnicianRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AssignedTicketService implements IAssignedTicketService {
    private final AssignedTicketRepository assignedTicketRepository;
    private final AssignedTicketMapper assignedTicketMapper;
    private final TicketRepository ticketRepository;
    private final TechnicianRepository technicianRepository;

    @Override
    public AssignedTicketResponse changeAssignedTicketStatus(UUID techId, UUID ticketId) {
        //----> Fetch the assigned-ticket with the given tech-id and ticket-id.
        var assignedTicket = this.getOneAssignedTicket(techId, ticketId);

        //----> Change ticket-status.
        var completed = !assignedTicket.getCompleted();
        assignedTicket.setCompleted(completed);
        var status = assignedTicket.getStatus() == Status.Closed ? Status.Open : Status.Closed;
        assignedTicket.setStatus(status);

        //----> Update assigned-ticket in db.
        var updatedAssignedTicket = assignedTicketRepository.save(assignedTicket);

        //----> Send back response.
        return ToAssignedTicketResponse.toAssignedTicketResponse(updatedAssignedTicket);
    }

    @Override
    public AssignedTicketResponse createAssignedTicket(AssignedTicketCreate request) {
        //----> Map the assigned-ticket-create to assigned-ticket.
        var assignedTicket = assignedTicketMapper.toEntity(request);

        //----> Get the technician and the ticket.
        var tech = technicianRepository.getTechnicianById(request.getTechId());
        var ticket = ticketRepository.findTicketById(request.getTicketId());
        assignedTicket.setTech(tech);
        assignedTicket.setTicket(ticket);

        //----> Insert the new assigned-ticket.
        var createdAssignedTicket = assignedTicketRepository.save(assignedTicket);

        //----> Send back response.
        return ToAssignedTicketResponse.toAssignedTicketResponse(createdAssignedTicket);
    }

    @Transactional
    @Override
    public AssignedTicketResponse deleteAssignedTicketById(UUID techId, UUID ticketId) {
        //----> Check for null assigned-ticket.
        assignedTicketRepository.deleteById(ticketId);

        //----> Delete the assigned ticket with the giving tech-id and ticket-id.
        var deletedAssignedTicket = assignedTicketRepository.deleteByTechIdAndTicketId(techId, ticketId);

        //----> Send back response.
        return ToAssignedTicketResponse.toAssignedTicketResponse(deletedAssignedTicket);
    }

    @Override
    public AssignedTicketResponse editAssignedTicketById(UUID techId, UUID ticketId, AssignedTicketEdit request) {
        //----> Map the assigned-ticket-edit to assigned-ticket.
        var assignedTicket = assignedTicketMapper.toEntity(request);

        //----> Get the technician and the ticket.
        var tech = technicianRepository.getTechnicianById(techId);
        var ticket = ticketRepository.findTicketById(ticketId);
        assignedTicket.setTech(tech);
        assignedTicket.setTicket(ticket);

        //----> Update the assigned-ticket.
        var updatedAssignedTicket = assignedTicketRepository.save(assignedTicket);

        //----> Send back response.
        return ToAssignedTicketResponse.toAssignedTicketResponse(updatedAssignedTicket);
    }

    @Override
    public AssignedTicketResponse getAssignedTicketById(UUID techId, UUID ticketId) {
        //----> Fetch the assigned-ticket by tech-id and ticket-id.
        var assignedTicket = this.getOneAssignedTicket(techId, ticketId);

        //----> Send back response.
        return ToAssignedTicketResponse.toAssignedTicketResponse(assignedTicket);
    }

    @Override
    public List<AssignedTicketResponse> getAllAssignedTicket() {
        //----> Fetch all the assigned-tickets.
        var allAssignedTickets = assignedTicketRepository.findAll();

        //----> Send back response.
        return allAssignedTickets.stream().map(ToAssignedTicketResponse::toAssignedTicketResponse).toList();
    }

    @Override
    public List<AssignedTicketResponse> getAssignedTicketsByTechId(UUID techId) {
        //----> Fetch tickets by tech-id.
        var tickets = assignedTicketRepository.findAssignedTicketsByTechId(techId);

        //----> Send back response.
        return tickets.stream().map(ToAssignedTicketResponse::toAssignedTicketResponse).toList();
    }

    @Override
    public List<AssignedTicketResponse> getAssignedTicketsByTicketId(UUID ticketId) {
        //----> Fetch tickets by ticket-id.
        var tickets = assignedTicketRepository.findAssignedTicketsByTicketId(ticketId);

        //----> Send back response.
        return tickets.stream().map(ToAssignedTicketResponse::toAssignedTicketResponse).toList();
    }

    @Override
    public List<AssignedTicketResponse> getCompletedAssignedTicket() {
        //----> Fetch completed assigned-tickets.
        var tickets = assignedTicketRepository.getCompletedTickets();

        //----> Send back response.
        return tickets.stream().map(ToAssignedTicketResponse::toAssignedTicketResponse).toList();
    }

    @Override
    public List<AssignedTicketResponse> getInCompletedAssignedTicket() {
        //----> Fetch all incompleted tickets.
        var tickets = assignedTicketRepository.getIncompleteTickets();

        //----> Send back response.
        return tickets.stream().map(ToAssignedTicketResponse::toAssignedTicketResponse).toList();
    }

    private AssignedTicket getOneAssignedTicket(UUID techId, UUID ticketId) {
        //----> Fetch assigned-ticket with the giving techId and ticketId.
        var assignedTicket = assignedTicketRepository.findByTechIdAndTicketId(techId, ticketId);

        //----> Check for null.
        if (assignedTicket == null) {
            throw new NotFoundException("AssignedTicket not found in db!");
        }

        //----> Send back response.
        return assignedTicket;
    }
}

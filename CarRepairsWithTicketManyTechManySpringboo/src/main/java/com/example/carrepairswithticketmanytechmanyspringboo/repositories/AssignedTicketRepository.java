package com.example.carrepairswithticketmanytechmanyspringboo.repositories;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.AssignedTicket;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Status;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AssignedTicketRepository extends JpaRepository<AssignedTicket, UUID> {
    List<AssignedTicket> findAssignedTicketsByTicketId(UUID ticketId);
    List<AssignedTicket> findAssignedTicketsByTechId(UUID techId);
    List<AssignedTicket> findAssignedTicketsByTechIdAndTicketId(UUID techId, UUID ticketId);
    AssignedTicket deleteByTechIdAndTicketId(UUID techId, UUID ticketId);
    AssignedTicket findByTechIdAndTicketId(UUID techId, UUID ticketId);

    @Query("SELECT t FROM AssignedTicket t WHERE  t.completed = true")
    List<AssignedTicket> getCompletedTickets();

    @Query("SELECT t FROM AssignedTicket t WHERE  t.completed = false")
    List<AssignedTicket> getIncompleteTickets();
}

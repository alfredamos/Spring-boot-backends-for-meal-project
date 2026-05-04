package com.example.carrepairswithticketmanytechmanyspringboo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "assignedTickets") // Customize the join table name
public class AssignedTicket {
    @EmbeddedId
    private AssignedTicketId id = new AssignedTicketId();

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "assignBy", nullable = false)
    private String assignBy;

    @CreationTimestamp
    @Column(name = "assignAt", updatable = false)
    private LocalDateTime assignAt = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.Open;

    @ManyToOne
    @MapsId("techId") // Maps the techId from the embedded id to this association
    @JoinColumn(name = "techId")
    private Technician tech;

    @ManyToOne
    @MapsId("ticketId") // Maps the ticketId from the embedded id to this association
    @JoinColumn(name = "ticketId")
    private Ticket ticket;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", insertable = false)
    private LocalDateTime updatedAt;
}

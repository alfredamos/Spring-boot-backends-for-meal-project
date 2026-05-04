package com.example.carrepairswithticketmanytechmanyspringboo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AssignedTicketId implements Serializable {
    @Column(name = "techId")
    private UUID techId;

    @Column(name = "ticketId")
    private UUID ticketId;
}

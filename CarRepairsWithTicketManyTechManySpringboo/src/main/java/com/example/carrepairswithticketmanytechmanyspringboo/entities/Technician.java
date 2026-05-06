package com.example.carrepairswithticketmanytechmanyspringboo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "technicians")
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String specialty;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id") // Foreign key column in 'technicians' table
    private User user;

    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "tech", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AssignedTicket> assignedTickets = new HashSet<>();
}

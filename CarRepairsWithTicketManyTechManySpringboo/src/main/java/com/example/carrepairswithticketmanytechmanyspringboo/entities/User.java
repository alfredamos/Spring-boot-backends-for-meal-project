package com.example.carrepairswithticketmanytechmanyspringboo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.User;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.Customer;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private LocalDateTime dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Token> tokens;

    // Optional: Reference to Customer
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Customer customer;

    // Optional: Reference to Technician
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Technician technician;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;
}


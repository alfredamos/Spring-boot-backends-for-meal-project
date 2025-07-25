package com.alfredamos.meal_order.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false)
  private String password;

  @Column()
  private Role role;

  @OneToMany(mappedBy = "user", orphanRemoval = true)
  private List<Order> orders;

  @OneToMany(mappedBy = "user")
  private List<Pizza> pizzas;

}

package com.alfredamos.meal_order.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pizzas")
@Entity
public class Pizza {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String topping;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private String image;

  @Column(nullable = false)
  private String description;

  @OneToMany(mappedBy = "pizza", orphanRemoval = true)
  private List<CartItem> cartItems = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}

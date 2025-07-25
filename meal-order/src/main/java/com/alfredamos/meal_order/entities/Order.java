package com.alfredamos.meal_order.entities;

import java.time.LocalDate;
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
@Table(name = "orders")
@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column
  private LocalDate orderDate;

  @Column
  private LocalDate deliveryDate;

  @Column
  private LocalDate shippingDate;

  @Column
  private String paymentId;

  @Column
  private Boolean isShipped;

  @Column
  private Boolean isDelivered;

  @Column
  private Boolean isPending;

  @Column
  private Integer totalQuantity;

  @Column
  private Double totalPrice;

  @Builder.Default
  @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE, orphanRemoval = true)
  private List<CartItem> cartItems = new ArrayList<>();

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  private Status status;

}

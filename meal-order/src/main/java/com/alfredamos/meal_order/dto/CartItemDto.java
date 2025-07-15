package com.alfredamos.meal_order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private String name;

    private String image;

    private Double price;

    private Integer quantity;

    private UUID pizzaId;

}

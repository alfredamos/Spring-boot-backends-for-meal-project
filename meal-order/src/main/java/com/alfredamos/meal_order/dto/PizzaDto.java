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
public class PizzaDto {
    private String name;

    private String topping;

    private Double price;

    private Integer quantity;

    private String image;

    private String description;

    private UUID userId;

}

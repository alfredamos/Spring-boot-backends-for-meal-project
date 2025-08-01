package com.alfredamos.meal_order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Topping is required.")
    private String topping;

    @Positive(message = "Price must be positive.")
    private Double price;

    @Positive(message = "Quantity must be greater than zero.")
    private Integer quantity;

    @NotBlank(message = "Image is required.")
    private String image;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotBlank(message = "UserId is required.")
    private UUID userId;

}

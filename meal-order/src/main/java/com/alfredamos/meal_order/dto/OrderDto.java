package com.alfredamos.meal_order.dto;


import com.alfredamos.meal_order.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private LocalDate orderDate;

    private LocalDate deliveryDate;

    private LocalDate shippingDate;

    private String paymentId;

    private Boolean isShipped;

    private Boolean isDelivered;

    private Boolean isPending;

    private Integer totalQuantity;

    private Double totalPrice;

    private Status status;

    private List<CartItemDto> cartItemsDto;

    private UUID userId;

}

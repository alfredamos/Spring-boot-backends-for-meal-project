package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private UUID orderId;
    private Status paymentStatus;
}

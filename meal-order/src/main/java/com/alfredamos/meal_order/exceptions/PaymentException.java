package com.alfredamos.meal_order.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentException extends RuntimeException{
    public PaymentException(String message) {
        super(message);
    }
}

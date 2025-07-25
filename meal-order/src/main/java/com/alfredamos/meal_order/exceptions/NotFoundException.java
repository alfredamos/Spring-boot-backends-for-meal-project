package com.alfredamos.meal_order.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String message;
}

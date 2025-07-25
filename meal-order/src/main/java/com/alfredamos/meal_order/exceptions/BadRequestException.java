package com.alfredamos.meal_order.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BadRequestException extends RuntimeException{
    private String message;
}

package com.alfredamos.meal_order.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ForbiddenException extends RuntimeException{
    private String message;
}

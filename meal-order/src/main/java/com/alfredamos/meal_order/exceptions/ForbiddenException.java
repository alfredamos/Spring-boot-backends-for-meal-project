package com.alfredamos.meal_order.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}

package com.alfredamos.meal_order.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnAuthorizedException extends RuntimeException{
    private String message;
}

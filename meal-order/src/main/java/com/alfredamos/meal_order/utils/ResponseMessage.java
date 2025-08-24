package com.alfredamos.meal_order.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private String status;
    private String message;
    private int statusCode;
}

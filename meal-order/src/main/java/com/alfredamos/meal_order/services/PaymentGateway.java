package com.alfredamos.meal_order.services;


import com.alfredamos.meal_order.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}

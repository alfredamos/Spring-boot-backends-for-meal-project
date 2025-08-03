package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.config.StripeConfig;
import com.alfredamos.meal_order.entities.CartItem;
import com.alfredamos.meal_order.entities.Order;
import com.alfredamos.meal_order.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Getter
@RequiredArgsConstructor
@Service
public class StripePaymentGateway implements PaymentGateway{
    private final StripeConfig stripeConfig;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        System.out.println("In stripe-payment-gateway, website : " + stripeConfig.getWebsiteUrl());
        System.out.println("In stripe-payment-gateway, order : " + order);
        try {
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)

                    .setSuccessUrl( stripeConfig.getWebsiteUrl()+ "/checkout-successUrl?orderId=" + order.getId())
                    .setCancelUrl(stripeConfig.getWebsiteUrl() + "/checkout-cancelUrl");

            order.getCartItems().forEach(item -> {
                var unitPrice = BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(100));
                System.out.println("In stripe-payment-gateway, unitPrice : " + unitPrice);
                var lineItem = createLineItem(item, unitPrice);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());

            return new CheckoutSession(session.getUrl());

        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }

    }

    private SessionCreateParams.LineItem createLineItem(CartItem item, BigDecimal unitPrice) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(
                        createPriceData(item, unitPrice)
                ).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CartItem item, BigDecimal unitPrice) {
        return SessionCreateParams.LineItem.PriceData.builder()
                //.setCurrency("usd")
                .setUnitAmountDecimal(unitPrice)
                .setProductData(
                        createProductData(item)
                ).build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(CartItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getName()).build();
    }
}

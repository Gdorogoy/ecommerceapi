package com.spihgor.payment.notification;

import com.spihgor.payment.payment.Payment;
import com.spihgor.payment.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail



        ) {
}

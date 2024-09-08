package com.spihgor.order.payment;

import com.spihgor.order.customer.CustomerResponse;
import com.spihgor.order.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}

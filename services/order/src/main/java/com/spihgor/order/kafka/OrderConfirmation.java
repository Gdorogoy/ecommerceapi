package com.spihgor.order.kafka;

import com.spihgor.order.customer.CustomerResponse;
import com.spihgor.order.order.PaymentMethod;
import com.spihgor.order.product.ProductPurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<ProductPurchaseResponse> product
) {


}

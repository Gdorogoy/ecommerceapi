package com.spihgor.cart.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId,
        List<PurchaseRequest> products
) {
}

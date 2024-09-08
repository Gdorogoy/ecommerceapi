package com.spihgor.order.order;

import com.spihgor.order.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Needs to be Payment")
        PaymentMethod paymentMethod,
        @NotNull(message = "Needs to be some Customer to buy")
        @NotEmpty(message = "Needs to be some Customer to buy")
        @NotBlank(message = "Needs to be some Customer to buy")
        String customerId,
        @NotEmpty(message = "You should buy at least one item")
        List<PurchaseRequest> products
) {
}

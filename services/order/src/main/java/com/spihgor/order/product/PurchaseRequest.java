package com.spihgor.order.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product needs to have an id")
        Integer productId,
        @Positive(message = "Quantity needs to be positive")
        double quantity
) {
}

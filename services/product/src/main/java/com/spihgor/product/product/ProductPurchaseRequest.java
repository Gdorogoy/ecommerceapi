package com.spihgor.product.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Id need to be valid")
        Integer productId,
        @NotNull(message = "Quantity need to be valid")
        double quantity
) {
}

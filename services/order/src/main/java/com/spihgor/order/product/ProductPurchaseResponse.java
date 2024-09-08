package com.spihgor.order.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        double quantity
) {
}

package com.spihgor.cart.product;

import java.math.BigDecimal;

public record ProductResponseCart(
        String imageUrl,
        String productName,
        double quantity,
        BigDecimal price
) {
}

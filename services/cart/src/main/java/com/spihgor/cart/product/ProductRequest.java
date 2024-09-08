package com.spihgor.cart.product;

public record ProductRequest(
        Integer productId,
        double quantity
) {
}

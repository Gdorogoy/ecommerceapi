package com.spihgor.cart.order;


public record PurchaseRequest(
        Integer productId,
        double quantity
) {
}

package com.spihgor.cart.cart;

import com.spihgor.cart.product.ProductResponseCart;

import java.util.List;

public record CartResponse(
        List<ProductResponseCart> productResponseCartList
) {
}

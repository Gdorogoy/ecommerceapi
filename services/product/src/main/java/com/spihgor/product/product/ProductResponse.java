package com.spihgor.product.product;

import java.math.BigDecimal;

public record ProductResponse (
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        String imageUrl,
        Integer categoryId,
        String categoryName,
        String categoryDescription
){
}

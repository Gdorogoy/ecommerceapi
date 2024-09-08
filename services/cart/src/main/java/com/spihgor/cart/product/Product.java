package com.spihgor.cart.product;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Validated
public class Product {

    private String imageUrl;
    private String productName;
    private Integer productId;
    private double quantity;
    private BigDecimal price;


}

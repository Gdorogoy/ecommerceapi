package com.spihgor.cart.cart;


import com.spihgor.cart.product.Product;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document
public class Cart {


    @Id
    private String id;
    private String customerId;
    private List<Product> productList;



}

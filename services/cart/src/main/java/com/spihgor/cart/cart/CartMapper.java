package com.spihgor.cart.cart;


import com.spihgor.cart.product.Product;
import com.spihgor.cart.product.ProductResponse;
import com.spihgor.cart.product.ProductResponseCart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartMapper {



    public CartResponse toResponse(Cart cart) {
        return new CartResponse(
                toProductResponse(cart.getProductList())
        );
    }

    private List<ProductResponseCart> toProductResponse(List<Product> productList) {
        return productList.stream().map(
                product -> toProductResponseSingle(product))
                .collect(Collectors.toList());
    }

    private ProductResponseCart toProductResponseSingle(Product product) {
        return new ProductResponseCart(
                product.getImageUrl(),
                product.getProductName(),
                product.getQuantity(),
                product.getPrice()
        );
    }


    public Product toProduct(ProductResponse product) {
        return new Product(
                product.imageUrl(),
                product.name(),
                product.id(),
                product.availableQuantity(),
                product.price()
        );
    }


}

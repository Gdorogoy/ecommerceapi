package com.spihgor.cart.cart;

import com.spihgor.cart.order.PaymentMethod;
import com.spihgor.cart.product.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @GetMapping("/{cart-id}")
    public ResponseEntity<CartResponse> viewCart(@PathVariable("cart-id") String cartId){
        return ResponseEntity.ok(cartService.findCart(cartId));
    }


    @PostMapping("/{id}")
    public ResponseEntity<Integer> addToCart(@PathVariable("id") String id,@RequestBody ProductRequest productRequestList){
        return ResponseEntity.ok(cartService.addToCart(id,productRequestList));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> clearCart(@PathVariable("id") String id){
        return ResponseEntity.ok(cartService.clearCart(id));
    }

    @PostMapping("/{customer-id}")
    public ResponseEntity<CartResponse> createCart(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(cartService.createCart(customerId));
    }

    @PostMapping("/{cart-id}")
    public ResponseEntity<Boolean> checkOut(@PathVariable("cart-id") String cartId, @RequestBody String paymentMethod){
        return ResponseEntity.ok(cartService.checkOut(cartId, paymentMethod));
    }

}

package com.spihgor.cart.order;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "order-service",
        url = "${application.config.order-url}"
)
public interface OrderAPIClient {

    public ResponseEntity<Integer> createOrder(@RequestBody OrderRequest request);
}

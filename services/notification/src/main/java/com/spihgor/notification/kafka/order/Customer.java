package com.spihgor.notification.kafka.order;


public record Customer(
        String firstname,
        String lastname,
        String email
) {
}

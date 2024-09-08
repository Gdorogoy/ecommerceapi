package com.spihgor.notification.kafka;

import com.spihgor.notification.email.EmailService;
import com.spihgor.notification.kafka.order.OrderConfirmation;
import com.spihgor.notification.kafka.payment.PaymentConfirmation;
import com.spihgor.notification.notification.Notification;
import com.spihgor.notification.notification.NotificationRepository;
import com.spihgor.notification.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService service;
    private final EmailService emailService;


    @KafkaListener(topics = "payment-topic", groupId = "paymentGroup")
    private void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming message from payment-topic Topic:: %s",paymentConfirmation));
        repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        // send email via email sender

        var customerName= paymentConfirmation.customerFirstname()+ " "+ paymentConfirmation.customerLastname();

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic",groupId = "orderGroup")
    private void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming message from order-topic Topic:: %s",orderConfirmation));
        repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // send email via email sender

        var customerName= orderConfirmation.customer().firstname()+ " "+ orderConfirmation.customer().lastname();

        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

    }
}

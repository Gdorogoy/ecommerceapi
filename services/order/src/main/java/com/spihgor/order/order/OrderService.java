package com.spihgor.order.order;

import com.spihgor.order.customer.CustomerClient;
import com.spihgor.order.exception.BusinessException;
import com.spihgor.order.kafka.OrderConfirmation;
import com.spihgor.order.kafka.OrderProducer;
import com.spihgor.order.orderline.OrderLineService;
import com.spihgor.order.orderline.OrderLineRequest;
import com.spihgor.order.payment.PaymentClient;
import com.spihgor.order.payment.PaymentRequest;
import com.spihgor.order.product.ProductClient;
import com.spihgor.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final orderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


    public Integer createOrder(OrderRequest request){
        //check if the customer exists (open feign)
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order No customer exists with id :"+ request.customerId()));

        //purchase the products --> product-service (open feign)
        var purchasedProducts =this.productClient.purchaseProducts(request.products()).getBody();

        //order the order
        var order = repository.save(mapper.toOrder(request));

        //continue order lines
        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }

        //payment process --> payment service (open feign)
        var paymentRequest= new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send order confirmation --> notification service (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());

    }

    public OrderResponse findById(Integer orderId) {
        return mapper.toOrderResponse(repository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException(
                        String.format("Order not found with id:", orderId)
                )));
    }
}

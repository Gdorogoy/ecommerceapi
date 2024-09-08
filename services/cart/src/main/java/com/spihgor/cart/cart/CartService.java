package com.spihgor.cart.cart;


import com.spihgor.cart.exception.EntityFoundWhenNotExcpectedException;
import com.spihgor.cart.exception.EntityNotFoundException;
import com.spihgor.cart.order.OrderAPIClient;
import com.spihgor.cart.order.OrderRequest;
import com.spihgor.cart.order.PaymentMethod;
import com.spihgor.cart.order.PurchaseRequest;
import com.spihgor.cart.product.ProductAPIClient;
import com.spihgor.cart.product.ProductRequest;
import com.spihgor.cart.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper mapper;
    private final CartRepository repository;
    private final ProductAPIClient productAPIClient;
    private final OrderAPIClient orderAPIClient;

    public CartResponse findCart(String cartId) {
        return mapper.toResponse(repository.findById(cartId).orElseThrow(()->new EntityNotFoundException(String.format(
                "Cannot find any cart with the id %s",cartId)
        )));
    }

    public Integer addToCart(String id, ProductRequest productRequestList) {
        Cart cart= repository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(
                         String.format("no cart found with id: %s",productRequestList.productId())));

        var products=cart.getProductList();

        ProductResponse product=productAPIClient.findById(productRequestList.productId()).getBody();

        if(product==null){
            throw new EntityNotFoundException(
                    String.format("no product found with id: %s",productRequestList.productId()));
        }

        products.add(mapper.toProduct(product));

        cart.setProductList(products);
        repository.save(cart);

        return productRequestList.productId();
    }

    public String clearCart(String id) {
        Cart cart= repository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(
                        String.format("no cart found with id: %s",id)));
        cart.setProductList(null);
        repository.save(cart);
        return "Cart with the id: " + id +" has been cleared";
    }

    public CartResponse createCart(String customerId) {
        if(repository.findByCustomerId(customerId)!=null){
            throw new EntityFoundWhenNotExcpectedException(
                    String.format("The card for customer with id: %s is already created",customerId)
            );
        }

        Cart cart=new Cart(null,customerId,null);
        repository.save(cart);

        return mapper.toResponse(cart);
    }

    public Boolean checkOut(String cartId, String paymentMethod) {
        // Retrieve the cart by ID
        Cart cart = repository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("no cart found with id: %s",cartId)));

        // Calculate the total amount
        BigDecimal totalAmount = cart.getProductList().stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Generate a reference for the order
        String orderReference = generateOrderReference();

        // Convert the product list to PurchaseRequest objects
        List<PurchaseRequest> purchaseRequests = cart.getProductList().stream()
                .map(product -> new PurchaseRequest(
                        product.getProductId(),
                        product.getQuantity()
                ))
                .collect(Collectors.toList());

        // Create the order request
        OrderRequest orderRequest = new OrderRequest(
                null,
                orderReference,
                totalAmount,
                getPaymentMethod(paymentMethod),
                cart.getCustomerId(),
                purchaseRequests
        );

        // Call the order service to create the order
        Integer orderId = orderAPIClient.createOrder(orderRequest).getBody();

        // If the order was created successfully, return true
        return orderId != null;
    }

    private String generateOrderReference() {
        return "ORD-" + UUID.randomUUID().toString();
    }

    private PaymentMethod getPaymentMethod(String paymentMethod) {
        try {
            return PaymentMethod.valueOf(paymentMethod.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }
    }

}

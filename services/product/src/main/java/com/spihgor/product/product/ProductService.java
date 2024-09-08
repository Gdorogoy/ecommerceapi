package com.spihgor.product.product;


import com.spihgor.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        return repository.save(mapper.toProduct(request)).getId();
    }


    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productsIds= request
                .stream()
                .map(ProductPurchaseRequest::productId).toList();
        var storedProducts= repository.findAllByIdInOrderById(productsIds);
        if(productsIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more products doesnt exists");
        }

        var storedRequest=request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
             var product=storedProducts.get(i);
             var productRequest=storedRequest.get(i);
             if(product.getAvailableQuantity()< productRequest.quantity()){
                 throw  new ProductPurchaseException("Not enough stock of the selected item: "+productRequest.productId());
             }
             var newAvailabeQuanity= product.getAvailableQuantity()-productRequest.quantity();
             product.setAvailableQuantity(newAvailabeQuanity);
             repository.save(product);
             purchasedProducts.add(mapper.toProductResponse(product,productRequest.quantity()));

        }
        return purchasedProducts;

    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id :"+productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper :: toResponse)
                .collect(Collectors.toList());
    }





}

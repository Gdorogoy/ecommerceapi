package com.spihgor.cart.cart;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {

    Cart findByCustomerId(String customerId);
}

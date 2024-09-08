package com.spihgor.customer.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer first name is required ")
        String firstname,
        @NotNull(message = "Customer last name is required ")
        String lastname,
        @Email(message = "Customer email is required ")
        String email,
        @NotNull(message = "Customer address is required ")
        Address address
) {

}

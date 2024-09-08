package com.spihgor.customer.customer;

import com.spihgor.customer.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer= repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer= repository.findById(request.id()).orElseThrow(()-> new CustomerNotFoundException(
                String.format("Cannot update customer,no customer found with this id %s",request.id())));
        mergerCustomer(customer,request);
        repository.save(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream().map(customer-> toCustomerResponse(customer)).toList();
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return toCustomerResponse(repository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(String.format(
                "Cannot find any customer with the id %s",customerId
        ))));
    }

    public void deleteById(String customerId) {
        repository.deleteById(customerId);
    }




    /*
    HELP METHODS
     */

    private CustomerResponse toCustomerResponse(Customer request) {
        CustomerResponse response=new CustomerResponse(
                request.getId(),
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getAddress());
        return response;

    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if(requestValidator(request)){
            customer.setFirstname(request.firstname());
            customer.setLastname(request.lastname());
            customer.setAddress(request.address());
            customer.setEmail(request.email());

        }
    }

    private boolean requestValidator(CustomerRequest request) {
        if(request.firstname().isBlank() ||
                request.lastname().isBlank() ||
                request.email().isBlank() ||
                request.address()==null){
            return false;
        }
        return true;
    }



}

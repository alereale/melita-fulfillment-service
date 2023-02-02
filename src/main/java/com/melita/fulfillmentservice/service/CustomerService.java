package com.melita.fulfillmentservice.service;

import com.melita.fulfillmentservice.exception.ResourceNotFoundException;
import com.melita.fulfillmentservice.exception.response.ErrorCode;
import com.melita.fulfillmentservice.model.Customer;
import com.melita.fulfillmentservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer customerByIdOrThrow(Long customerId) throws ResourceNotFoundException{
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new ResourceNotFoundException(String.format("Customer id %s does not exist.", customerId), ErrorCode.NOT_FOUND);
        return customer.get();
    }

}

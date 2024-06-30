package com.web_shop.shop.converter;

import com.web_shop.shop.dto.AddressRequest;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Address;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressConverter {

    private final CustomerRepository customerRepository;

    @Autowired
    public AddressConverter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Address toAddress(AddressRequest addressRequest) {
        Optional<Customer> customer = customerRepository.findById(addressRequest.getCustomerId());
        if (customer.isEmpty()) {
            throw new RecordNotFoundException(String.format("Customer with id %s not found", addressRequest.getCustomerId()));
        }
        Address address = new Address();
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setStreet(addressRequest.getStreet());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setCustomer(customer.get());
        return address;
    }
}

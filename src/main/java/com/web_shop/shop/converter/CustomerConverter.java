package com.web_shop.shop.converter;

import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.model.Address;
import com.web_shop.shop.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerConverter {
    public static Customer toCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        List<Address> addresses = customer.getAddresses();
        customer.setAddresses(addresses);
        return customer;
    }
}

package com.web_shop.shop.converter;

import com.web_shop.shop.condig.JwtService;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.enums.Role;
import com.web_shop.shop.model.Address;
import com.web_shop.shop.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerConverter {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Customer toCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customer.setRole(Role.ROLE_USER);
        List<Address> addresses = customer.getAddresses();
        customer.setAddresses(addresses);
        return customer;
    }

    public CustomerResponse toResponse(Customer savedCustomer) {
        String jwtToken = jwtService.generateJwtToken(savedCustomer);
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(savedCustomer.getId());
        customerResponse.setJwt(jwtToken);
        return customerResponse;
    }
}

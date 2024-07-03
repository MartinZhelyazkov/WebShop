package com.web_shop.shop.service;

import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.dto.LoginRequest;
import com.web_shop.shop.model.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerRequest customerRequest);

    String login(LoginRequest loginRequest);

    CustomerResponse findCustomerById(Long id);

    List<CustomerResponse> findAllCustomer();

    void updateCustomer(CustomerRequest customerRequest, Long id);

    void delCustomerById(Long id);

    Customer findByEmail(String email);

}

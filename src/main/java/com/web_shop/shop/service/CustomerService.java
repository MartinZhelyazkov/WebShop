package com.web_shop.shop.service;

import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerRequest customerRequest);

    CustomerResponse findCustomerById(Long id);

    List<CustomerResponse> findAllCustomer();

    void updateCustomer(CustomerRequest customerRequest, Long id);

    void delCustomerById(Long id);

}

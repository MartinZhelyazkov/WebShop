package com.web_shop.shop.service;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.model.Customer;
import java.util.Set;
public interface CustomerService {
    CustomerResponse findById(Long id);
    CustomerResponse addCustomer(CustomerRequest customerRequest);
    Set<Customer> findAllCustomer();
    CustomerResponse updateCustomer(CustomerRequest customerRequest,Long id);
    void delCustomerById(Long id);
}

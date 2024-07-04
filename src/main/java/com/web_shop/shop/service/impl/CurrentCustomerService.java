package com.web_shop.shop.service.impl;

import com.web_shop.shop.model.Customer;
import com.web_shop.shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentCustomerService {

    private final CustomerService customerService;

    @Autowired
    public CurrentCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Boolean isCurrentUserMatchOwner(Customer productOwner) {
        Customer currentCustomer = extractCurrentCustomer();
        if (currentCustomer != null && productOwner != null) {
            return productOwner.getEmail().equals(extractCurrentCustomer().getEmail());
        }
        return false;
    }

    public Customer extractCurrentCustomer() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (Customer) principal;
        } else {
            return customerService.findByEmail(principal.toString());
        }

    }
}

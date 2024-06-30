package com.web_shop.shop.service.impl;

import com.web_shop.shop.converter.CustomerConverter;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.advice.exception.EmailAlreadyExistException;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.repository.CustomerRepository;
import com.web_shop.shop.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }

    @Override
    public void addCustomer(CustomerRequest customerRequest) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerRequest.getEmail());
        if (existingCustomer.isPresent()) {
            throw new EmailAlreadyExistException(String.format("This email %s already exists", customerRequest.getEmail()));
        } else {
            Customer customer = CustomerConverter.toCustomer(customerRequest);
            customerRepository.save(customer);
        }
    }

    @Override
    public CustomerResponse findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Customer with id %s not found", id)));
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        return customerResponse;
    }

    @Override
    public List<CustomerResponse> findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerResponse customerResponse = new CustomerResponse();
            BeanUtils.copyProperties(customer, customerResponse);
            customerResponseList.add(customerResponse);
        }
        return customerResponseList;
    }

    @Override
    public void updateCustomer(CustomerRequest customerRequest, Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Customer with id %s does not exist", id)));
        Optional<Customer> alreadyExistingCustomer = customerRepository.findByEmail(customerRequest.getEmail());
        if (alreadyExistingCustomer.isPresent()) {
            throw new EmailAlreadyExistException(String.format("This email %s already exists", customerRequest.getEmail()));
        } else {
            existingCustomer.setFirstName(customerRequest.getFirstName());
            existingCustomer.setLastName(customerRequest.getLastName());
            existingCustomer.setEmail(customerRequest.getEmail());
            customerRepository.save(existingCustomer);
        }
    }

    @Override
    public void delCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}


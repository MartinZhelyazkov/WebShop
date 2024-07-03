package com.web_shop.shop.service.impl;

import com.web_shop.shop.advice.exception.EmailAlreadyExistException;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.condig.JwtService;
import com.web_shop.shop.converter.CustomerConverter;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.dto.LoginRequest;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.repository.CustomerRepository;
import com.web_shop.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new EmailAlreadyExistException("User with such email already exists");
        }
        Customer customer = customerConverter.toCustomer(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return customerConverter.toResponse(savedCustomer);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        ));

        var user = customerRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RecordNotFoundException("User not found or wrong password"));
        return jwtService.generateJwtToken(user);
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

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).get();
    }

}


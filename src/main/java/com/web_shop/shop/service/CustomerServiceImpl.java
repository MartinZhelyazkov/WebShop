package com.web_shop.shop.service;
import com.web_shop.shop.converter.CustomerConverter;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.exception.RecordNotFoundException;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerConverter customerConverter;
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerConverter customerConverter, CustomerRepository customerRepository) {
        this.customerConverter = customerConverter;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Customer with id %s not found", id)));
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        return customerResponse;
    }

    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerConverter.toCustomer(customerRequest);
        Customer existingCustomer = customerRepository.save(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(existingCustomer, customerResponse);
        return customerResponse;
    }

    @Override
    public Set<Customer> findAllCustomer() {
        return new HashSet<>(customerRepository.findAll());
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest, Long id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            throw new RecordNotFoundException(String.format("Customer with id %s does not exist", id));
        }
        Customer customer = existingCustomer.get();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setAddress(customerRequest.getAddress());
        customer.setEmail(customerRequest.getEmail());
        customerRepository.save(customer);
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customer,customerResponse);
        return customerResponse;
    }

    @Override
    public void delCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}

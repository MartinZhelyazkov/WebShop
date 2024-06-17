package com.web_shop.shop.converter;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.model.Customer;
import org.springframework.stereotype.Component;
@Component
public class CustomerConverter {
    public Customer toCustomer(CustomerRequest customerRequest){
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddress(customerRequest.getAddress());
        return customer;
    }
}

package com.web_shop.shop.controller;
import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.service.CustomerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    CustomerServiceImpl customerServiceImp;
    @GetMapping("/{customerId}")
    ResponseEntity<CustomerResponse>findById(@Valid @PathVariable("customerId") Long customerId){
        return new ResponseEntity<>(customerServiceImp.findById(customerId), HttpStatus.FOUND);
    }
    @PostMapping
    ResponseEntity<CustomerResponse>addCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        return new ResponseEntity<>(customerServiceImp.addCustomer(customerRequest),HttpStatus.CREATED);
    }
    @GetMapping
    ResponseEntity<Set<Customer>>findAllCustomers(){
        return new ResponseEntity<>(customerServiceImp.findAllCustomer(),HttpStatus.FOUND);
    }
    @PostMapping("/{customerId}")
    ResponseEntity<CustomerResponse>updateCustomer(@Valid @RequestBody CustomerRequest customerRequest,
                                           @PathVariable("customerId") Long id){
        return new ResponseEntity<>(customerServiceImp.updateCustomer(customerRequest,id),HttpStatus.OK);
    }
    @DeleteMapping(path={"/{customerId}"})
    void delCustomerById(@PathVariable("customerId") Long id){
        customerServiceImp.delCustomerById(id);
    }
}

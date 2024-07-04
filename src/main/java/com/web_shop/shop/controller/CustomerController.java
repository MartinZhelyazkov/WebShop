package com.web_shop.shop.controller;

import com.web_shop.shop.dto.CustomerRequest;
import com.web_shop.shop.dto.CustomerResponse;
import com.web_shop.shop.dto.LoginRequest;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.service.CustomerService;
import com.web_shop.shop.service.impl.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@Tag(name = "Customer API", description = "API for managing customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    @Operation(summary = "Creating customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer successfully added"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class)))
    })
    ResponseEntity<CustomerResponse> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
          return new ResponseEntity<>(customerService.addCustomer(customerRequest),HttpStatus.CREATED);
    }

    @PostMapping(path = "/auth")
    ResponseEntity<String> login (@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(customerService.login(loginRequest),HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Finding customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Customer successfully found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class)))
    })
    ResponseEntity<CustomerResponse> findCustomerById(@Valid @PathVariable("customerId") Long customerId) {
        return new ResponseEntity<>(customerService.findCustomerById(customerId), HttpStatus.FOUND);
    }

    @GetMapping
    @Operation(summary = "Finding all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "All customers found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class)))
    })
    ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomer(), HttpStatus.FOUND);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{customerId}")
    @Operation(summary = "Updating customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer successfully updated"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class)))
    })
    void updateCustomer(@Valid @RequestBody CustomerRequest customerRequest,
                        @PathVariable("customerId") Long id) {
        customerService.updateCustomer(customerRequest, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    @Operation(summary = "Deleting Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer successfully deleted"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Customer.class)))
    })
    void delCustomerById(@PathVariable("customerId") Long id) {
        customerService.delCustomerById(id);
    }
}

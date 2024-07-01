package com.web_shop.shop.service.impl;

import com.web_shop.shop.converter.AddressConverter;
import com.web_shop.shop.dto.AddressRequest;
import com.web_shop.shop.dto.AddressResponse;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Address;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.repository.AddressRepository;
import com.web_shop.shop.repository.CustomerRepository;
import com.web_shop.shop.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    private final CustomerRepository customerRepository;

    private final AddressConverter addressConverter;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, CustomerRepository customerRepository, AddressConverter addressConverter) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.addressConverter = addressConverter;
    }

    @Override
    public void addAddress(AddressRequest addressRequest) {
        Optional<Customer> existingCustomer = customerRepository.findById(addressRequest.getCustomerId());
        if (existingCustomer.isEmpty()) {
            throw new RecordNotFoundException(String.format("Customer with id %S not found", addressRequest.getCustomerId()));
        } else {
            Customer customer = existingCustomer.get();
            Address address = addressConverter.toAddress(addressRequest);
            address.setCustomer(customer);
            addressRepository.save(address);
        }
    }

    @Override
    public AddressResponse findAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RecordNotFoundException(String.format("Address with id %S not found", addressId)));
        AddressResponse addressResponse = new AddressResponse();
        BeanUtils.copyProperties(address, addressResponse);
        return addressResponse;
    }

    @Override
    public List<AddressResponse> findAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressResponse> addressResponseList = new ArrayList<>();
        for (Address address : addresses) {
            AddressResponse addressResponse = new AddressResponse();
            BeanUtils.copyProperties(address, addressResponse);
            addressResponseList.add(addressResponse);
        }
        return addressResponseList;
    }

    @Override
    public void updateAddress(AddressRequest addressRequest, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RecordNotFoundException(String.format("Address with id %S not found", addressId)));
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setStreet(addressRequest.getStreet());
        address.setPostalCode(addressRequest.getPostalCode());
        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}

package com.web_shop.shop.service;

import com.web_shop.shop.dto.AddressRequest;
import com.web_shop.shop.dto.AddressResponse;

import java.util.List;

public interface AddressService {
    void addAddress(AddressRequest addressRequest);

    AddressResponse findAddressById(Long addressId);

    List<AddressResponse> findAllAddresses();

    void updateAddress(AddressRequest addressRequest, Long addressId);

    void deleteAddress(Long addressId);

}

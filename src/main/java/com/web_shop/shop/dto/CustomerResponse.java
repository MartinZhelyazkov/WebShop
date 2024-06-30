package com.web_shop.shop.dto;

import com.web_shop.shop.model.Address;
import com.web_shop.shop.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Address> addresses;
    private List<Order> orders;
}

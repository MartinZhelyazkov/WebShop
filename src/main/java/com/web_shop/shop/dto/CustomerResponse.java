package com.web_shop.shop.dto;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}

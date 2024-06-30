package com.web_shop.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    @NotNull(message = "First name can't be null")
    @Size(min = 2, max = 150, message = "First name must be between 2 and 150 characters")
    private String firstName;
    @NotNull(message = "Last name can't be null")
    @Size(min = 2, max = 150, message = "Last name must be between 2 and 150 characters")
    private String lastName;
    @Email
    @NotNull(message = "Email can't be null")
    private String email;
}

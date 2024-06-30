package com.web_shop.shop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    @NotNull(message = "Street can't be null")
    @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
    private String street;
    @NotNull(message = "City can't be null")
    @Size(min = 2, max = 200, message = "City must be between 2 and 200 characters")
    private String city;
    @NotNull(message = "Country can't be null")
    @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
    private String country;
    @NotNull(message = "PostalCode can't be null")
    @Pattern(regexp = "[0-9]+", message = "Postcode can be only numbers")
    private String postalCode;
    @NotNull(message = "Address must be to a customer")
    private Long customerId;
}

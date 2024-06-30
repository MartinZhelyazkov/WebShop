package com.web_shop.shop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    @NotNull(message = "Product name can't be null")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;
    @NotNull(message = "Price can't be null")
    @Positive(message = "Price must be positive")
    private double price;
    @NotNull(message = "Product must be to an order")
    private Long orderId;
}

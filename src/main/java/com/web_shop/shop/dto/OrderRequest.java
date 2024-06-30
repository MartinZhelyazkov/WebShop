package com.web_shop.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    @NotEmpty(message = "Product list must not be empty")
    private List<@NotNull(message = "Product id must not be null") Long> productId;
    @NotNull(message = "Order must have customer")
    private Long customerId;
}

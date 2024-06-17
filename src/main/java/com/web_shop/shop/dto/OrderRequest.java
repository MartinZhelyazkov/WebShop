package com.web_shop.shop.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "Total amount can't be null")
    private double totalAmount;
}

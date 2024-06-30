package com.web_shop.shop.dto;

import com.web_shop.shop.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Instant orderDate;
    private double totalAmount;
    private List<Product> products;
}

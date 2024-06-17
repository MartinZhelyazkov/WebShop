package com.web_shop.shop.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
@Getter
@Setter
public class OrderResponse{
    private Long id;
    private Instant orderDate;
    private double totalAmount;
}

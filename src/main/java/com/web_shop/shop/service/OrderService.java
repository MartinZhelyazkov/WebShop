package com.web_shop.shop.service;

import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    void addOrder(OrderRequest orderRequest);

    OrderResponse findOrderById(Long id);

    List<OrderResponse> findAllOrders();

    void updateOrder(OrderRequest orderRequest, Long id);

    void deleteOrder(Long id);
}

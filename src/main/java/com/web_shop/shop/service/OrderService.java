package com.web_shop.shop.service;
import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.dto.OrderResponse;
import com.web_shop.shop.model.Order;
import java.util.Set;
public interface OrderService {
    OrderResponse findOrderById(Long id);
    OrderResponse addOrder(OrderRequest orderRequest);
    Set<Order>findAllOrders();
    OrderResponse updateOrder(OrderRequest orderRequest,Long id);
    void deleteOrder(Long id);
}

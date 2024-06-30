package com.web_shop.shop.converter;

import com.web_shop.shop.dto.ProductRequest;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductConverter {

    private final OrderRepository orderRepository;

    @Autowired
    public ProductConverter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Product toProduct(ProductRequest productRequest) {
        Optional<Order> order = orderRepository.findById(productRequest.getOrderId());
        if (order.isEmpty()) {
            throw new RecordNotFoundException(String.format("Order with id %s not found", productRequest.getOrderId()));
        }
        Order existingOrder = order.get();
        List<Order> orders = new ArrayList<>();
        orders.add(existingOrder);
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setOrders(orders);
        return product;
    }

}

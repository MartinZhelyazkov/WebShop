package com.web_shop.shop.service.impl;

import com.web_shop.shop.converter.OrderConverter;
import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.dto.OrderResponse;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.repository.CustomerRepository;
import com.web_shop.shop.repository.OrderRepository;
import com.web_shop.shop.repository.ProductRepository;
import com.web_shop.shop.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderConverter orderConverter, OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderConverter = orderConverter;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addOrder(OrderRequest orderRequest) {
        Optional<Customer> existingCustomer = customerRepository.findById(orderRequest.getCustomerId());
        if (existingCustomer.isEmpty()) {
            throw new RecordNotFoundException(String.format("Customer with id %S not found", orderRequest.getCustomerId()));
        }else {
            Customer customer = existingCustomer.get();
            Order order = orderConverter.toOrder(orderRequest);
            order.setCustomer(customer);
            orderRepository.save(order);
        }
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Order with id %s not found", id)));
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse orderResponse = new OrderResponse();
            BeanUtils.copyProperties(order, orderResponse);
            orderResponseList.add(orderResponse);
        }
        return orderResponseList;
    }

    @Override
    public void updateOrder(OrderRequest orderRequest, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Order with id %s does not exist", id)));
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new RecordNotFoundException(String.format("Customer with id %s does not exist", orderRequest.getCustomerId())));
        order.setCustomer(customer);
        List<Product> products = productRepository.findAllById(orderRequest.getProductId());
        order.setProducts(new ArrayList<>(products));
        Instant newDate = Instant.now();
        order.setOrderDate(newDate);
        order.setTotalAmount(order.getTotalAmount());
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

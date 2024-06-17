package com.web_shop.shop.service;
import com.web_shop.shop.converter.OrderConverter;
import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.dto.OrderResponse;
import com.web_shop.shop.exception.RecordNotFoundException;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderConverter orderConverter;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderConverter orderConverter, OrderRepository orderRepository) {
        this.orderConverter = orderConverter;
        this.orderRepository = orderRepository;
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
    public OrderResponse addOrder(OrderRequest orderRequest) {
        Order order = orderConverter.toOrder(orderRequest);
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(savedOrder, orderResponse);
        return orderResponse;
    }

    @Override
    public Set<Order> findAllOrders() {
        return new HashSet<>(orderRepository.findAll());
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format("Order with id %s does not exist", id)));
        Instant newDate = Instant.now();
        order.setOrderDate(newDate);
        order.setTotalAmount(orderRequest.getTotalAmount());
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        orderRepository.save(order);
        return orderResponse;
    }
    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

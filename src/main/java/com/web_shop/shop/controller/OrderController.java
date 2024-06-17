package com.web_shop.shop.controller;
import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.dto.OrderResponse;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    OrderServiceImpl orderServiceImpl;

    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponse> findOrderById(@PathVariable("orderId") Long id) {
        return new ResponseEntity<>(orderServiceImpl.findOrderById(id), HttpStatus.FOUND);
    }

    @PostMapping
    ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderServiceImpl.addOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<Set<Order>> findAllOrders() {
        return new ResponseEntity<>(orderServiceImpl.findAllOrders(), HttpStatus.FOUND);
    }

    @PostMapping("/{orderId}")
    ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest,
                                              @PathVariable("orderId") Long id) {
        return new ResponseEntity<>(orderServiceImpl.updateOrder(orderRequest,id), HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}")
    void deleteOrder(@PathVariable("orderId") Long id){
        orderServiceImpl.deleteOrder(id);
    }
}

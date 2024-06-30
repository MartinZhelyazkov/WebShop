package com.web_shop.shop.converter;

import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Customer;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.repository.CustomerRepository;
import com.web_shop.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class OrderConverter {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderConverter(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        Instant orderDate = Instant.now();
        Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerId());
        if (customer.isEmpty()) {
            throw new RecordNotFoundException(String.format("Customer with id %s not found", orderRequest.getCustomerId()));
        }
        List<Long> productIds = orderRequest.getProductId();
        List<Product> products = productRepository.findAllById(productIds);
        double totalAmount = 0.0;
        for (Product product : products) {
            totalAmount += product.getPrice();
        }
        order.setTotalAmount(totalAmount);
        order.setOrderDate(orderDate);
        order.setProducts(products);
        order.setCustomer(customer.get());
        return order;
    }
}

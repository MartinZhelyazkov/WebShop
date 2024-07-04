package com.web_shop.shop.converter;

import com.web_shop.shop.dto.ProductRequest;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.service.impl.CurrentCustomerService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductConverter {

    private final CurrentCustomerService customerService;

    public ProductConverter(CurrentCustomerService customerService) {
        this.customerService = customerService;
    }


    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCustomer(customerService.extractCurrentCustomer());
        return product;
    }

    public Set<Product> toProductsList(Set<ProductRequest> productRequests){
        Set<Product> products = new HashSet<>();
        for (ProductRequest productReq: productRequests) {
            products.add(toProduct(productReq));
        }
        return products;
    }

}

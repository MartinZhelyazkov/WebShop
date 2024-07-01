package com.web_shop.shop.converter;

import com.web_shop.shop.dto.ProductRequest;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductConverter {

    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
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

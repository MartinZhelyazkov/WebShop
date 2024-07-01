package com.web_shop.shop.service.impl;

import com.web_shop.shop.converter.ProductConverter;
import com.web_shop.shop.dto.ProductRequest;
import com.web_shop.shop.dto.ProductResponse;
import com.web_shop.shop.advice.exception.RecordNotFoundException;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.repository.OrderRepository;
import com.web_shop.shop.repository.ProductRepository;
import com.web_shop.shop.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final OrderRepository orderRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addProduct(ProductRequest productRequest) {
        Product product = productConverter.toProduct(productRequest);
        productRepository.save(product);
    }

    @Override
    public void addProducts(Set<ProductRequest> productRequest) {
        productRepository.saveAll(productConverter.toProductsList(productRequest));
    }


    @Override
    public ProductResponse findProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Product with id %S not found", productId)));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public List<ProductResponse> findAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponsesList = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponses = new ProductResponse();
            BeanUtils.copyProperties(product, productResponses);
            productResponsesList.add(productResponses);
        }
        return productResponsesList;
    }

    @Override
    public void updateProduct(ProductRequest productRequest, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new RecordNotFoundException(String.format("Product with id %S not found", productId)));
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
    }

    @Override
    public void delProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}

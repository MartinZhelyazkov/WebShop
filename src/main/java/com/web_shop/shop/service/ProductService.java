package com.web_shop.shop.service;

import com.web_shop.shop.dto.ProductRequest;
import com.web_shop.shop.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void addProduct(ProductRequest productRequest);

    ProductResponse findProductById(Long productId);

    List<ProductResponse> findAllProduct();

    void updateProduct(ProductRequest productRequest, Long productId);

    void delProduct(Long productId);
}

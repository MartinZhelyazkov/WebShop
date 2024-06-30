package com.web_shop.shop.controller;

import com.web_shop.shop.dto.ProductRequest;
import com.web_shop.shop.dto.ProductResponse;
import com.web_shop.shop.model.Product;
import com.web_shop.shop.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product API", description = "API for managing products")
public class ProductController {
    @Autowired
    ProductServiceImpl productServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Creating product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully added"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class)))
    })
    void addProduct(@Valid @RequestBody ProductRequest productRequest) {
        productServiceImpl.addProduct(productRequest);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Finding product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Product successfully found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class)))
    })
    ResponseEntity<ProductResponse> findProductById(@Valid @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productServiceImpl.findProductById(productId), HttpStatus.FOUND);
    }

    @GetMapping
    @Operation(summary = "Finding all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "All products found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class)))
    })
    ResponseEntity<List<ProductResponse>> findAllProduct() {
        return new ResponseEntity<>(productServiceImpl.findAllProduct(), HttpStatus.FOUND);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updating product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully updated"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class)))
    })
    void updateProduct(@Valid @RequestBody ProductRequest productRequest,
                       @PathVariable("productId") Long productId) {
        productServiceImpl.updateProduct(productRequest, productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleting product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Product.class)))
    })
    void delProduct(@PathVariable("productId") Long productId) {
        productServiceImpl.delProduct(productId);
    }
}

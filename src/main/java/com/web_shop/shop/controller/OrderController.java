package com.web_shop.shop.controller;

import com.web_shop.shop.dto.OrderRequest;
import com.web_shop.shop.dto.OrderResponse;
import com.web_shop.shop.model.Order;
import com.web_shop.shop.service.impl.OrderServiceImpl;
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
@RequestMapping("api/v1/order")
@Tag(name = "Order API", description = "API for managing orders")
public class OrderController {
    @Autowired
    OrderServiceImpl orderServiceImpl;

    @PostMapping
    @Operation(summary = "Creating order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully added"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    void addOrder(@Valid @RequestBody OrderRequest orderRequest) {
        orderServiceImpl.addOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Finding order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Order successfully found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class)))
    })
    ResponseEntity<OrderResponse> findOrderById(@Valid @PathVariable("orderId") Long id) {
        return new ResponseEntity<>(orderServiceImpl.findOrderById(id), HttpStatus.FOUND);
    }

    @GetMapping
    @Operation(summary = "Finding all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "All orders found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class)))
    })
    ResponseEntity<List<OrderResponse>> findAllOrders() {
        return new ResponseEntity<>(orderServiceImpl.findAllOrders(), HttpStatus.FOUND);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Updating order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order successfully updated"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class)))
    })
    void updateOrder(@Valid @RequestBody OrderRequest orderRequest,
                     @PathVariable("orderId") Long id) {
        orderServiceImpl.updateOrder(orderRequest, id);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Deleting order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order successfully deleted"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Order.class)))
    })
    void deleteOrder(@PathVariable("orderId") Long id) {
        orderServiceImpl.deleteOrder(id);
    }
}

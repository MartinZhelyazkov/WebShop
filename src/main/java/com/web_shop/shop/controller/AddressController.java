package com.web_shop.shop.controller;

import com.web_shop.shop.dto.AddressRequest;
import com.web_shop.shop.dto.AddressResponse;
import com.web_shop.shop.model.Address;
import com.web_shop.shop.service.impl.AddressServiceImpl;
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
@RequestMapping("/api/v1/address")
@Tag(name = "Address API", description = "API for managing addresses")
public class AddressController {
    @Autowired
    AddressServiceImpl addressServiceimpl;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creating address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address successfully added"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class)))
    })
    @PostMapping
    void addAddress(@Valid @RequestBody AddressRequest addressRequest) {
        addressServiceimpl.addAddress(addressRequest);
    }

    @Operation(summary = "Finding address by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Address successfully found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class)))
    })
    @GetMapping("/{addressId}")
    ResponseEntity<AddressResponse> findAddressById(@Valid @PathVariable("addressId") Long addressId) {
        return new ResponseEntity<>(addressServiceimpl.findAddressById(addressId), HttpStatus.FOUND);
    }

    @Operation(summary = "Finding all addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "All addresses found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class)))
    })
    @GetMapping
    ResponseEntity<List<AddressResponse>> findAllAddresses() {
        return new ResponseEntity<>(addressServiceimpl.findAllAddresses(), HttpStatus.FOUND);
    }

    @Operation(summary = "Updating address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address successfully updated"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "404", description = "Not found"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class)))
    })
    @PutMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    void updateAddress(@Valid @RequestBody AddressRequest addressRequest,
                       @PathVariable("addressId") Long addressId) {
        addressServiceimpl.updateAddress(addressRequest, addressId);
    }

    @Operation(summary = "Deleting address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address successfully deleted"
                    , content = @Content(mediaType = "application/json"
                    , schema = @Schema(implementation = Address.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{addressId}")
    void deleteAddress(@PathVariable Long addressId) {
        addressServiceimpl.deleteAddress(addressId);
    }
}

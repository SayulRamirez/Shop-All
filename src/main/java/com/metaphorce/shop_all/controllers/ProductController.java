package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.ProductResponse;
import com.metaphorce.shop_all.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Operation(summary = "Show the products",
            description = "Displays a list of all the products that are for sale in the store, which you can add to the cart",
            responses = @ApiResponse(description = "You get a list of the products or if there are no products it will be an empty list",
                    responseCode = "200"))
    @GetMapping
    public ResponseEntity<List<ProductResponse>> showProducts() {
        return ResponseEntity.ok(productService.getAll());
    }
}

package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.ProductResponse;
import com.metaphorce.shop_all.domain.RestockRequest;
import com.metaphorce.shop_all.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Restock the stock of the products",
            responses = {@ApiResponse(description = "If the producto was not found", responseCode = "404"),
                    @ApiResponse(description = "If the operation is successful", responseCode = "200")},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/restock")
    public ResponseEntity<Void> restock(@RequestBody RestockRequest request) {

        productService.restock(request);

        return ResponseEntity.ok().build();
    }

}

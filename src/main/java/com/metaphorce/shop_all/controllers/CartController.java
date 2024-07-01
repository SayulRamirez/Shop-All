package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.domain.CartDetailsResponse;
import com.metaphorce.shop_all.domain.CartResponse;
import com.metaphorce.shop_all.services.CartServiceImpl;
import com.metaphorce.shop_all.services.interfaces.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    public final CartService cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Add products to cart", description = "Add the products to the cart using their ID, user ID, and number of product pieces",
            responses = {@ApiResponse(description = "if the cart or product was not found with the ID provided", responseCode = "404", content = @Content),
                    @ApiResponse(description = "if the parts ordered are greater than the actual stock", responseCode = "422", content = @Content),
                    @ApiResponse(description = "If add product to cart is successful", responseCode = "200")
            }, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@Valid  @RequestBody AddCartRequest request) {
        cartService.addProduct(request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get the general cart details",
            responses = {@ApiResponse(description = "if the cart was not found with the ID provider", responseCode = "404", content = @Content),
                    @ApiResponse(description = "If request is successful", responseCode = "200")}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/general/{id}")
    public ResponseEntity<CartResponse> getCart(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                    @PathVariable Long id) {
        CartResponse cart = cartService.getCart(id);

        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Get the cart details", description = "Get specific cart details from a user using their id",
            responses = {@ApiResponse(description = "if the cart was not found with the ID provider", responseCode = "404", content = @Content),
                    @ApiResponse(description = "If the request is successful", responseCode = "200")}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/details/{userId}")
    public ResponseEntity<List<CartDetailsResponse>> getDetailsCart(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                                        @PathVariable Long userId) {

        return ResponseEntity.ok(cartService.getDetailsCart(userId));
    }

    @Operation(summary = "Remove a product form the cart", description = "Remove a product from the user's cart using the user ID and the product",
            responses = {@ApiResponse(description = "if remove product is successful", responseCode = "404")}, security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(path = "/user/{userId}/product/{productId}")
    public ResponseEntity<Void> removeProduct(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                  @PathVariable Long userId,
                                              @Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                  @PathVariable Long productId) {

        cartService.deleteProduct(productId, userId);

        return ResponseEntity.notFound().build();
    }
}

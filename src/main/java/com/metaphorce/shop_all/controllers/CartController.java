package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.domain.CartDetailsResponse;
import com.metaphorce.shop_all.domain.CartResponse;
import com.metaphorce.shop_all.services.CartServiceImpl;
import com.metaphorce.shop_all.services.interfaces.CartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    public final CartService cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Agrega productos al carrito", description = "Agrega los productos al carrito utilizando su id, el id del usuario y la cantidad de pizas del producto")
    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@Valid  @RequestBody AddCartRequest request) {
        cartService.addProduct(request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtiene los detalles generales del carrito")
    @GetMapping(path = "/general/{id}")
    public ResponseEntity<CartResponse> getCart(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                    @PathVariable Long id) {
        CartResponse cart = cartService.getCart(id);

        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Obtiene los detalles del carrito", description = "Obtiene los detalles especifícos del carrito de un usuario por medio de su id")
    @GetMapping(path = "/details/{userId}")
    public ResponseEntity<List<CartDetailsResponse>> getDetailsCart(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                                        @PathVariable Long userId) {

        return ResponseEntity.ok(cartService.getDetailsCart(userId));
    }

    @Operation(summary = "Remueve un producto del carrito", description = "Remueve un producto del carrito del usuario por medio del id del usuario y el producto")
    @DeleteMapping(path = "/user/{userId}/product/{productId}")
    public ResponseEntity<Void> removeProduct(@Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                  @PathVariable Long userId,
                                              @Valid @NotNull(message = "The field must not be null") @Min(message = "The value must be greater that 0", value = 1)
                                                  @PathVariable Long productId) {

        cartService.deleteProduct(productId, userId);

        return ResponseEntity.notFound().build();
    }
}

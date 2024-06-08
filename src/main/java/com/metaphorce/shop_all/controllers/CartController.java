package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.domain.CartDetailsResponse;
import com.metaphorce.shop_all.domain.CartResponse;
import com.metaphorce.shop_all.services.CartServiceImpl;
import com.metaphorce.shop_all.services.interfaces.CartService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Agrega productos al carrito", description = "Agrega los productos al carrito utilizando su id, el id del usuario y la cantidad de pizas del producto")
    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@RequestBody AddCartRequest request) {
        cartService.addProduct(request);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtiene los detalles generales del carrito")
    @GetMapping(path = "/general/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long id) {
        CartResponse cart = cartService.getCart(id);

        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Obtiene los detalles del carrito", description = "Obtiene los detalles especifícos del carrito de un usuario por medio de su id")
    @GetMapping(path = "/details/{userId}")
    public ResponseEntity<List<CartDetailsResponse>> getDetailsCart(@PathVariable Long userId) {

        return ResponseEntity.ok(cartService.getDetailsCart(userId));
    }

    @Operation(summary = "Remueve un producto del carrito", description = "Remueve un producto del carrito del usuario por medio del id del usuario y el producto")
    @DeleteMapping(path = "/user/{userId}/product/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long userId, @PathVariable Long productId) {

        cartService.deleteProduct(productId, userId);

        return ResponseEntity.notFound().build();
    }
}

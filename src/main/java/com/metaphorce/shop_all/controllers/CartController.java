package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.domain.CartDetailsResponse;
import com.metaphorce.shop_all.domain.CartResponse;
import com.metaphorce.shop_all.services.CartServiceImpl;
import com.metaphorce.shop_all.services.interfaces.CartService;
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

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToCart(@RequestBody AddCartRequest request) {
        cartService.addProduct(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/general/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long id) {
        CartResponse cart = cartService.getCart(id);

        return ResponseEntity.ok(cart);
    }

    @GetMapping(path = "/details/{userId}")
    public ResponseEntity<List<CartDetailsResponse>> getDetailsCart(@PathVariable Long userId) {

        return ResponseEntity.ok(cartService.getDetailsCart(userId));
    }
}

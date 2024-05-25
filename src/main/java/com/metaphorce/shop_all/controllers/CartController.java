package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.AddCartRequest;
import com.metaphorce.shop_all.services.CartServiceImpl;
import com.metaphorce.shop_all.services.interfaces.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

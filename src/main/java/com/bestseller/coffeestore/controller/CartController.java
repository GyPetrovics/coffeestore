package com.bestseller.coffeestore.controller;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.model.CartSummary;
import com.bestseller.coffeestore.service.CartService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(path = "/addCartItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartSummary> addCartOrderItem(
            @RequestBody CartOrderCreation cartOrderCreation
    ) {
        if (cartOrderCreation == null) {
            // throw Exception (a custom exception could be created here...)
        }
        return ResponseEntity.ok(cartService.addCartOrderItem(cartOrderCreation));

    }
}

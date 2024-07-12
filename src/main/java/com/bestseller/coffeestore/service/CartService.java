package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.model.CartSummary;
import lombok.NonNull;

public interface CartService {
    CartSummary addCartOrderItem(@NonNull CartOrderCreation cartOrderCreation);
}

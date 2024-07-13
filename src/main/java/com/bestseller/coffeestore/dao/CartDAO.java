package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.entity.Cart;

public interface CartDAO {
    void save(Cart cart);
    Cart findByUserId(String userId);
    Boolean cartContainsOrderItem(String userId, CartOrderCreation cartOrderCreation);
    void clearUserCart(Cart cart);
}

package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.entity.Cart;

public interface CartDAO {
    void save(Cart cart);
    Cart findByUserId(String userId);
}

package com.bestseller.coffeestore.mock;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.dao.CartDAO;
import com.bestseller.coffeestore.entity.Cart;
import com.bestseller.coffeestore.entity.CartOrderItems;

import java.util.List;

public class MockCartDAO implements CartDAO {
    @Override
    public void save(Cart cart) {

    }

    @Override
    public Cart findByUserId(String userId) {
        return null;
    }

    @Override
    public Boolean cartContainsOrderItem(String userId, CartOrderCreation cartOrderCreation) {
        return null;
    }

    @Override
    public void clearUserCart(Cart cart) {

    }

    @Override
    public List<CartOrderItems> getAllCartOrderItems() {
        return List.of();
    }
}

package com.bestseller.coffeestore.dao;

import com.bestseller.coffeestore.controller.bean.CartOrderCreation;
import com.bestseller.coffeestore.entity.CartOrderItems;

public interface CartOrderItemDAO {
    void save(CartOrderItems cartOrderItems);
}

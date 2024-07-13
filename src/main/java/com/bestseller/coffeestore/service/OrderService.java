package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.FinalizeOrder;
import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.entity.Cart;
import com.bestseller.coffeestore.model.OrderSummary;
import lombok.NonNull;

public interface OrderService {

    OrderSummary createOrder(@NonNull OrderCreation orderCreation, Cart cart);
    OrderSummary finalizeOrders(FinalizeOrder finalizeOrders);
}

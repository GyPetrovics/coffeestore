package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dto.OrderDTO;
import com.bestseller.coffeestore.model.OrderSummary;
import lombok.NonNull;

public interface OrderService {

    OrderSummary createOrder(@NonNull OrderCreation orderCreation);
}

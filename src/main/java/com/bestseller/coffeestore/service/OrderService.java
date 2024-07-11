package com.bestseller.coffeestore.service;

import com.bestseller.coffeestore.controller.bean.OrderCreation;
import com.bestseller.coffeestore.dto.OrderDTO;
import lombok.NonNull;

public interface OrderService {

    OrderDTO createOrder(@NonNull OrderCreation orderCreation);
}

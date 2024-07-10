package com.bestseller.coffeestore.controller.bean;

import com.bestseller.coffeestore.dto.OrderItemDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreation {
    private String userId;
    private List<OrderItemDTO> orderItems;
}

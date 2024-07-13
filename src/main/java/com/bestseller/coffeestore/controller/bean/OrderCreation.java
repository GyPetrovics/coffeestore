package com.bestseller.coffeestore.controller.bean;

import com.bestseller.coffeestore.dto.OrderItemDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreation {
    private String userId;
    private List<OrderItemDTO> orderItems;
}
